package net.dayner.api.domain.paymentArchive.strategy;

import net.dayner.api.domain.creditCard.entity.GiftCardTransaction;
import net.dayner.api.domain.paymentArchive.entity.PaymentArchive;
import net.dayner.api.domain.paymentArchive.entity.PaymentType;
import org.springframework.stereotype.Component;

@Component
public class GiftCardTransactionStrategy implements PaymentArchiveStrategy<GiftCardTransaction> {
    @Override
    public Class<GiftCardTransaction> getSupportedType() {
        return GiftCardTransaction.class;
    }
    @Override
    public PaymentArchive convertToArchive(GiftCardTransaction transaction, String phoneNumber) {
        GiftCardTransaction giftCardTransaction = transaction;
        return PaymentArchive.builder()
                .paymentType(PaymentType.GIFT_CARD)
                .phoneNumber(phoneNumber)
                .date(giftCardTransaction.getTransactionDate())
                .identificationNumber(giftCardTransaction.getGiftCard().getCardNumber())
                .amount(giftCardTransaction.getAmount())
                .description(giftCardTransaction.getTransactionType().getTitle())
                .build();
    }
}

