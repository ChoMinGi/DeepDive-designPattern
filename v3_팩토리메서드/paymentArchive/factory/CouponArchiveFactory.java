package net.dayner.api.domain.paymentArchive.factory;

import net.dayner.api.domain.coupon.entity.Coupon;
import net.dayner.api.domain.paymentArchive.entity.PaymentArchive;
import net.dayner.api.domain.paymentArchive.entity.PaymentType;
import org.springframework.stereotype.Component;

@Component
public class CouponArchiveFactory implements PaymentArchiveFactory<Coupon>{
    @Override
    public PaymentArchive convertToPaymentArchive(Coupon transaction, String phoneNumber) {
        return PaymentArchive.builder()
                .paymentType(PaymentType.COUPON)
                .phoneNumber(phoneNumber)
                .date(transaction.getUpdatedAt())
                .identificationNumber(transaction.getCouponNumber())
                .amount(transaction.getUsageAmount())
                .description(transaction.isUsed() ? "쿠폰 사용" : "쿠폰 만료")
                .build();
    }
}
