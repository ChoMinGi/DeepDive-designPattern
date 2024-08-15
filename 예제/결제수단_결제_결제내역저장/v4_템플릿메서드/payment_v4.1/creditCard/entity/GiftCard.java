package net.dayner.api.domain.payment.creditCard.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.dayner.api.domain.payment.DaynerPayment;
import net.dayner.api.domain.payment.creditCard.dto.GiftCardAdminRequest;
import net.dayner.api.domain.user.entity.User;
import net.dayner.api.utils.BaseEntity;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "gift_card_tb")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCard extends BaseEntity implements DaynerPayment {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = true)
    private String imageUrl;

    @Column(nullable = true)
    private String buyer;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private int balance;

    @Column(nullable = false)
    private CardStatus status;

    @Column(nullable = true)
    private LocalDateTime expiryDate;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = {CascadeType.MERGE})
    @JsonIgnore
    private User user;
    public void activateGiftCard(GiftCardAdminRequest.GiftCardActiveDTO giftCardActiveDTO){
        this.balance = giftCardActiveDTO.balance();
        this.status = CardStatus.ACTIVE;
        this.expiryDate = LocalDateTime.now().plusYears(1);
        this.buyer = giftCardActiveDTO.phoneNumber();
    }
    @Override
    public void use(int newBalance){
        this.balance=newBalance;
    }

    @Override
    public void registration(User user) {this.user = user;}

    @Override
    public void reset(){
        this.imageUrl = null;
        this.buyer = null;
        this.balance=0;
        this.status = CardStatus.DEACTIVATE;
        this.expiryDate = null;
        this.user = null;
    }
    @Override
    public void updateImage(String imageUrl) {this.imageUrl = imageUrl;}
}
