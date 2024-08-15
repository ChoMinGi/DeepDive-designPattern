package net.dayner.api.domain.payment.creditCard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.dayner.api.domain.payment.creditCard.dto.GiftCardAdminRequest;
import net.dayner.api.domain.payment.creditCard.dto.GiftCardAdminResponse;
import net.dayner.api.domain.payment.creditCard.dto.GiftCardRequest;
import net.dayner.api.domain.payment.creditCard.dto.GiftCardResponse;
import net.dayner.api.domain.payment.creditCard.entity.CardStatus;
import net.dayner.api.domain.payment.creditCard.entity.GiftCard;
import net.dayner.api.domain.payment.creditCard.entity.GiftCardTransaction;
import net.dayner.api.domain.payment.creditCard.entity.TransactionType;
import net.dayner.api.domain.payment.creditCard.repository.GiftCardRepository;
import net.dayner.api.domain.payment.creditCard.repository.GiftCardTransactionRepository;
import net.dayner.api.domain.payment.paymentArchive.PaymentArchiveService;
import net.dayner.api.domain.user.UserService;
import net.dayner.api.domain.user.entity.User;
import net.dayner.api.exception.ErrorMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static net.dayner.api.utils.PromotionalCodeUtils.generateValidCardNumber;

@Service
@RequiredArgsConstructor
public class GiftCardCommandService {
    private final GiftCardRepository giftCardRepository;
    private final GiftCardTransactionRepository giftCardTransactionRepository;
    private final UserService userService;
    private final PaymentArchiveService paymentArchiveService;
    private final GiftCardQueryService giftCardQueryService;


    public List<GiftCard> createGiftCards(int quantity) {
        List<GiftCard> giftCards = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            String cardNumber;
            do {
                cardNumber = generateValidCardNumber();
            } while (giftCardRepository.existsByCardNumber(cardNumber));

            GiftCard giftCard = GiftCard.builder()
                    .cardNumber(cardNumber)
                    .balance(0)
                    .status(CardStatus.DEACTIVATE)
                    .build();

            giftCards.add(giftCardRepository.save(giftCard));
        }
        return giftCards;
    }

    @Transactional
    public List<GiftCardAdminResponse.GiftCardAdminDTO> activateMultiGiftCard(List<GiftCardAdminRequest.GiftCardActiveDTO> giftCardsActiveDTO){
        return giftCardsActiveDTO.stream()
                .map(this::activateGiftCardAndProcessTransaction)
                .toList();
    }
    @Transactional
    public GiftCardAdminResponse.GiftCardAdminDTO activateSingleGiftCard(GiftCardAdminRequest.GiftCardActiveDTO giftCardsActiveDTO){
        return activateGiftCardAndProcessTransaction(giftCardsActiveDTO);
    }
    private GiftCardAdminResponse.GiftCardAdminDTO activateGiftCardAndProcessTransaction(GiftCardAdminRequest.GiftCardActiveDTO giftCardsActiveDTO) {
        GiftCard giftCard = giftCardQueryService.getGiftCardByCardNumber(giftCardsActiveDTO.cardNumber());
        if (giftCard.getStatus() == CardStatus.ACTIVE) {
            throw new IllegalStateException(ErrorMessage.CARD_STATUS_ALREADY_ACTIVATE);
        }
        processGiftCardTransaction(giftCard, giftCardsActiveDTO.balance(), TransactionType.CHARGE);
        giftCard.activateGiftCard(giftCardsActiveDTO);
        giftCard.convertToPaymentArchive(giftCardsActiveDTO.phoneNumber(),TransactionType.CHARGE.getTitle(), giftCardsActiveDTO.balance());
        return new GiftCardAdminResponse.GiftCardAdminDTO(giftCard);
    }

    @Transactional
    public GiftCard transactGiftCard(GiftCardAdminRequest.GiftCardTransactionDTO giftCardTransactionDTO) {
        GiftCard giftCard = giftCardQueryService.getGiftCardByCardNumber(giftCardTransactionDTO.cardNumber());
        TransactionType transactionType = TransactionType.fromTitle(giftCardTransactionDTO.transactionType());

        int updatedAmount;
        if (transactionType == TransactionType.EXPIRATION) {
            updatedAmount = -giftCard.getBalance();
            giftCard.use(0);
        } else {
            updatedAmount = transactionType.getTransactionSign() * giftCardTransactionDTO.amount();
            if (giftCard.getBalance() + updatedAmount < 0) {
                throw new IllegalArgumentException(ErrorMessage.CARD_HAS_NO_MONEY);
            }

            giftCard.use(giftCard.getBalance() + updatedAmount);
        }
        processGiftCardTransaction(giftCard, updatedAmount, transactionType);
        return giftCardRepository.save(giftCard);
    }

    private void processGiftCardTransaction(GiftCard giftCard, int amount, TransactionType transactionType) {
        GiftCardTransaction transaction = GiftCardTransaction.builder()
                .giftCard(giftCard)
                .amount(amount)
                .transactionDate(LocalDateTime.now())
                .transactionType(transactionType)
                .build();
        giftCardTransactionRepository.save(transaction);
    }

    @Transactional
    public GiftCardResponse.GiftCardDTO registrationGiftCard(GiftCardRequest.GiftCardRegistrationDTO giftCardRegistrationDTO, String email){
        User user = userService.getUserByEmail(email);
        GiftCard giftCard = giftCardQueryService.getGiftCardByCardNumber(giftCardRegistrationDTO.cardNumber());
        if (giftCard.getUser() != null) {
            throw new IllegalArgumentException(ErrorMessage.CARD_ALREADY_REGISTRATION);
        }
        giftCard.registration(user);
        return new GiftCardResponse.GiftCardDTO(giftCard);
    }

    @Transactional
    public GiftCardResponse.GiftCardDTO resetCard(String cardNumber) {
        GiftCard giftCard = giftCardQueryService.getGiftCardByCardNumber(cardNumber);

        List<GiftCardTransaction> giftCardTransactionList = giftCardTransactionRepository.findByGiftCardOrderByTransactionDateDesc(giftCard);
        giftCardTransactionRepository.deleteAll(giftCardTransactionList);

        giftCard.reset();
        giftCardRepository.save(giftCard);

        return new GiftCardResponse.GiftCardDTO(giftCard);
    }

    @Transactional
    public void updateGiftCardImage(GiftCardAdminRequest.GiftCardImageUpdateDTO giftCardImageUpdateDTO) {
        GiftCard giftCardToUpdate = giftCardQueryService.getGiftCardByCardNumber(giftCardImageUpdateDTO.giftCardNumber());
        giftCardToUpdate.updateImage(giftCardImageUpdateDTO.newImageUrl());
        giftCardRepository.save(giftCardToUpdate);
    }

}


