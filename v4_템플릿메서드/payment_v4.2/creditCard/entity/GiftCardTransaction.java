package net.dayner.api.domain.payment.creditCard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "gift_card_transaction_tb")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardTransaction {
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
}
