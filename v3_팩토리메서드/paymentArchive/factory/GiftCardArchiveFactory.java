package net.dayner.api.domain.paymentArchive.factory;

import net.dayner.api.domain.creditCard.entity.GiftCardTransaction;
import net.dayner.api.domain.paymentArchive.entity.PaymentArchive;
import net.dayner.api.domain.paymentArchive.entity.PaymentType;
import org.springframework.stereotype.Component;

@Component
public class GiftCardArchiveFactory implements PaymentArchiveFactory<GiftCardTransaction>{
    @Override
    public PaymentArchive convertToPaymentArchive(GiftCardTransaction transaction, String phoneNumber) {
        return PaymentArchive.builder()
                .paymentType(PaymentType.GIFT_CARD)
                .phoneNumber(phoneNumber)
                .date(transaction.getTransactionDate())
                .identificationNumber(transaction.getGiftCard().getCardNumber())
                .amount(transaction.getAmount())
                .description(transaction.getTransactionType().getTitle())
                .build();


    }
}
