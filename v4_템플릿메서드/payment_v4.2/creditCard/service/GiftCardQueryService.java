package net.dayner.api.domain.payment.creditCard.service;

import lombok.RequiredArgsConstructor;
import net.dayner.api.domain.payment.creditCard.dto.GiftCardAdminResponse;
import net.dayner.api.domain.payment.creditCard.dto.GiftCardResponse;
import net.dayner.api.domain.payment.creditCard.entity.CardStatus;
import net.dayner.api.domain.payment.creditCard.entity.GiftCard;
import net.dayner.api.domain.payment.creditCard.entity.GiftCardTransaction;
import net.dayner.api.domain.payment.creditCard.repository.GiftCardRepository;
import net.dayner.api.domain.payment.creditCard.repository.GiftCardTransactionRepository;
import net.dayner.api.domain.user.UserService;
import net.dayner.api.domain.user.entity.User;
import net.dayner.api.exception.ErrorMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GiftCardQueryService {
    private final GiftCardRepository giftCardRepository;
    private final GiftCardTransactionRepository giftCardTransactionRepository;
    private final UserService userService;

    public List<GiftCardAdminResponse.GiftCardAdminDTO> getAllGiftCards() {
        return giftCardRepository.findAll().stream()
                .map(GiftCardAdminResponse.GiftCardAdminDTO::new)
                .toList();
    }

    public List<GiftCardResponse.GiftCardDTO> getAllGiftCardsByUser(String email) {
        User user = userService.getUserByEmail(email);
        return giftCardRepository.findAllByUser(user).stream()
                .map(GiftCardResponse.GiftCardDTO::new)
                .toList();
    }

    public GiftCardAdminResponse.GiftCardAdminDTO getTaggedCard(String cardNumber) {
        return new GiftCardAdminResponse.GiftCardAdminDTO(getGiftCardByCardNumber(cardNumber));
    }

    public GiftCardAdminResponse.GiftCardHistoryListAdminDTO getTaggedCardAdminHistoryList(String cardNumber) {
        List<GiftCardTransaction> transactions = giftCardTransactionRepository.findAllTransactionsByCardNumber(cardNumber);
        if (transactions.isEmpty()) {
            throw new NoSuchElementException(ErrorMessage.CARD_STATUS_NOT_ACTIVATE);
        }
        return new GiftCardAdminResponse.GiftCardHistoryListAdminDTO(transactions);
    }

    public GiftCardResponse.GiftCardHistoryListDTO getTaggedCardSimpleHistoryList(UUID cardUuid) {
        GiftCard giftCard = getGiftCardByCardUUid(cardUuid);
        if (giftCard.getStatus() == CardStatus.DEACTIVATE) {
            return new GiftCardResponse.GiftCardHistoryListDTO(giftCard);
        } else {
            return new GiftCardResponse.GiftCardHistoryListDTO(giftCardTransactionRepository.findAllTransactionsByGiftCardId(cardUuid));
        }
    }

    public GiftCardResponse.GiftCardHistorySimpleListDTO getTaggedCardSimpleSimpleHistoryList(UUID cardUuid) {
        GiftCard giftCard = getGiftCardByCardUUid(cardUuid);
        if (giftCard.getStatus() == CardStatus.DEACTIVATE) {
            return new GiftCardResponse.GiftCardHistorySimpleListDTO(giftCard);
        } else {
            return new GiftCardResponse.GiftCardHistorySimpleListDTO(giftCard, giftCardTransactionRepository.findByGiftCardOrderByTransactionDateDesc(giftCard));
        }
    }

    public GiftCard getGiftCardByCardNumber(String cardNumber) {
        return giftCardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessage.CARD_NOT_FOUND));
    }
    private GiftCard getGiftCardByCardUUid(UUID cardUuid) {
        return giftCardRepository.findById(cardUuid)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessage.CARD_NOT_FOUND));
    }

}
