package net.dayner.api.domain.paymentArchive;

import lombok.RequiredArgsConstructor;
import net.dayner.api.domain.coupon.entity.Coupon;
import net.dayner.api.domain.creditCard.entity.GiftCardTransaction;
import net.dayner.api.domain.paymentArchive.factory.CouponArchiveFactory;
import net.dayner.api.domain.paymentArchive.factory.GiftCardArchiveFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentArchiveService {
    private final PaymentArchiveRepository paymentArchiveRepository;
    private final GiftCardArchiveFactory giftCardArchiveFactory;
    private final CouponArchiveFactory couponArchiveFactory;
    public void saveGiftCardToPaymentArchive(GiftCardTransaction giftCardTransaction, String phoneNumber){
        paymentArchiveRepository.save(giftCardArchiveFactory.convertToPaymentArchive(giftCardTransaction,phoneNumber));
    }
    public void saveCouponToPaymentArchive(Coupon coupon, String phoneNumber){
        paymentArchiveRepository.save(couponArchiveFactory.convertToPaymentArchive(coupon,phoneNumber));
    }

    // PaymentArchive 의 Read
}
