package net.dayner.api.domain.payment.coupon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.dayner.api.domain.payment.DaynerPayment;
import net.dayner.api.domain.payment.PaymentArchivable;
import net.dayner.api.domain.payment.paymentArchive.entity.PaymentArchive;
import net.dayner.api.domain.payment.paymentArchive.entity.PaymentType;
import net.dayner.api.domain.user.entity.User;
import net.dayner.api.utils.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "coupon_tb")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coupon extends BaseEntity implements PaymentArchivable, DaynerPayment {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = true)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = {CascadeType.MERGE})
    @JsonIgnore
    private User giver;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JsonIgnore
    private User receiver;

    @Column(nullable = false)
    private String couponNumber;

    @Column(nullable = false)
    private boolean isPrepaid;

    @Column(nullable = false)
    private boolean isUsed;

    @Column(nullable = true)
    private LocalDateTime expiryDate;

    @Column(nullable = true)
    private LocalDateTime usedDate;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String giverNickname;

    @Column(nullable = false)
    private int maxUsageAmount;

    @Column(nullable = false)
    private int usageAmount;

    @Override
    public void use(int newBalance){
        this.usageAmount = newBalance;
        this.isUsed = true;
        this.usedDate = LocalDateTime.now();
    }

    @Override
    public void registration(User receiver){
        this.receiver = receiver;
    }

    @Override
    public void reset() {
        this.imageUrl=null;
        this.giver=null;
        this.receiver = null;
        this.isPrepaid = false;
        this.isUsed = false;
        this.expiryDate=null;
        this.usedDate=null;
        this.description=null;
        this.giverNickname=null;
        this.maxUsageAmount=0;
        this.usageAmount=0;
    }

    @Override
    public void updateImage(String newImageUrl){
        this.imageUrl = newImageUrl;
    }

    @Override
    public PaymentArchive convertToPaymentArchive(String phoneNumber) {
        return PaymentArchive.builder()
                .paymentType(PaymentType.COUPON)
                .phoneNumber(phoneNumber)
                .identificationNumber(couponNumber)
                .amount(usageAmount)
                .date(getUpdatedAt())
                .description(isUsed ? "쿠폰 사용" : "쿠폰 구매")
                .build();
    }
}
