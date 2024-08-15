package net.dayner.api.domain.payment.coupon.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.dayner.api.domain.payment.coupon.CouponRepository;
import net.dayner.api.domain.payment.coupon.dto.CouponAdminRequest;
import net.dayner.api.domain.payment.coupon.dto.CouponRequest;
import net.dayner.api.domain.payment.coupon.dto.CouponResponse;
import net.dayner.api.domain.payment.coupon.entity.Coupon;
import net.dayner.api.domain.user.UserService;
import net.dayner.api.domain.user.entity.User;
import net.dayner.api.exception.ErrorMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static net.dayner.api.utils.PromotionalCodeUtils.generateValidCardNumber;

@Service
@RequiredArgsConstructor
public class CouponCommandService {
    private final CouponRepository couponRepository;
    private final UserService userService;
    private final CouponQueryService couponQueryService;

    @Transactional
    public List<Coupon> createCoupons(CouponAdminRequest.CouponIssueDTO giftCouponIssueDTO) {
        List<Coupon> coupons = new ArrayList<>();
        User giver = userService.getUserByPhoneNumber(giftCouponIssueDTO.giverPhoneNumber());
        for (int i = 0; i < giftCouponIssueDTO.quantity(); i++) {
            String couponNumber;
            do {
                couponNumber = generateValidCardNumber();
            } while (couponRepository.existsByCouponNumber(couponNumber));

            Coupon coupon = Coupon.builder()
                    .couponNumber(couponNumber)
                    .giver(giver)
                    .description(giftCouponIssueDTO.description())
                    .giverNickname(giftCouponIssueDTO.giverNickname())
                    .maxUsageAmount(giftCouponIssueDTO.maxUsageAmount())
                    .isPrepaid(giftCouponIssueDTO.isPrepaid())
                    .expiryDate(LocalDateTime.now().plusMonths(giftCouponIssueDTO.expiryMonths()))
                    .isUsed(false)
                    .build();

            coupons.add(couponRepository.save(coupon));
        }
        return coupons;
    }

    @Transactional
    public void updateCouponsImage(CouponAdminRequest.CouponsImageUpdateDTO couponsImageUpdateDTO){
        List<Coupon> couponsToUpdate = couponRepository.findByCouponNumberIn(couponsImageUpdateDTO.couponNumberList());

        if (couponsToUpdate.isEmpty()) {
            throw new IllegalStateException(ErrorMessage.COUPON_NOT_FOUND);
        }
        couponsToUpdate.forEach(coupon -> coupon.updateImage(couponsImageUpdateDTO.newImageUrl()));
        couponRepository.saveAll(couponsToUpdate);
    }

    @Transactional
    public Coupon usingCoupon(CouponAdminRequest.CouponUsageDTO couponUsageDTO) {
        Coupon coupon = couponQueryService.getCouponByCouponNumber(couponUsageDTO.couponNumber());
        coupon.use(couponUsageDTO.usageAmount());
        return coupon;
    }

    @Transactional
    public CouponResponse.CouponDTO registrationCoupon(CouponRequest.CouponRegistrationDTO couponRegistrationDTO, String email) {
        User user = userService.getUserByEmail(email);
        Coupon coupon = couponQueryService.getCouponByCouponNumber(couponRegistrationDTO.couponNumber());
        if (coupon.getReceiver() != null) {
            throw new IllegalArgumentException(ErrorMessage.COUPON_ALREADY_REGISTRATION);
        }
        coupon.registration(user);
        return new CouponResponse.CouponDTO(coupon);
    }

    @Transactional
    public CouponResponse.CouponDTO resetCoupon(String couponNumber) {
        Coupon coupon = couponQueryService.getCouponByCouponNumber(couponNumber);

        coupon.convertToPaymentArchive(coupon.getGiver().getPhoneNumber().toString());
        coupon.reset();
        couponRepository.save(coupon);
        return new CouponResponse.CouponDTO(coupon);
    }


}
