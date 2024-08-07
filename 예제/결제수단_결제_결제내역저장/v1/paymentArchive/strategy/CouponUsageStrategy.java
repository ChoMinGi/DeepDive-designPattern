package net.dayner.api.domain.paymentArchive.strategy;

import net.dayner.api.domain.coupon.entity.Coupon;
import net.dayner.api.domain.paymentArchive.entity.PaymentArchive;
import net.dayner.api.domain.paymentArchive.entity.PaymentType;
import org.springframework.stereotype.Component;

@Component
public class CouponUsageStrategy implements PaymentArchiveStrategy<Coupon> {
    @Override
    public Class<Coupon> getSupportedType() {
        return Coupon.class;
    }
    @Override
    public PaymentArchive savePaymentRecord(Coupon transaction, String phoneNumber) {
        Coupon coupon = transaction;
        return PaymentArchive.builder()
                .paymentType(PaymentType.COUPON)
                .phoneNumber(phoneNumber)
                .date(coupon.getUpdatedAt())
                .identificationNumber(coupon.getCouponNumber())
                .amount(coupon.getUsageAmount())
                .description(coupon.isUsed() ? "쿠폰 사용" : "쿠폰 만료")
                .build();
    }
}

