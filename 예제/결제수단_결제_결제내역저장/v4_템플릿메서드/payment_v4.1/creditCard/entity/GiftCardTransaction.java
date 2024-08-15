package net.dayner.api.domain.payment.creditCard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.dayner.api.domain.payment.PaymentArchivable;
import net.dayner.api.domain.payment.paymentArchive.entity.PaymentArchive;
import net.dayner.api.domain.payment.paymentArchive.entity.PaymentType;

import java.time.LocalDateTime;

@Entity
@Table(name = "gift_card_transaction_tb")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardTransaction implements PaymentArchivable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private GiftCard giftCard;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @Column(nullable = false)
    private TransactionType transactionType;
    @Override
    public PaymentArchive convertToPaymentArchive(String phoneNumber) {
        return PaymentArchive.builder()
                .paymentType(PaymentType.GIFT_CARD)
                .phoneNumber(phoneNumber)
                .identificationNumber(giftCard.getCardNumber())
                .amount(amount)
                .date(transactionDate)
                .description(transactionType.getTitle())
                .build();
    }
}
