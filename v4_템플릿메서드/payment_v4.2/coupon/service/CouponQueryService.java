package net.dayner.api.domain.payment.coupon.service;

import lombok.RequiredArgsConstructor;
import net.dayner.api.domain.payment.coupon.CouponRepository;
import net.dayner.api.domain.payment.coupon.dto.CouponAdminResponse;
import net.dayner.api.domain.payment.coupon.dto.CouponResponse;
import net.dayner.api.domain.payment.coupon.entity.Coupon;
import net.dayner.api.domain.user.UserService;
import net.dayner.api.domain.user.entity.User;
import net.dayner.api.exception.ErrorMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponQueryService {
    private final CouponRepository couponRepository;
    private final UserService userService;

    public List<CouponResponse.CouponDTO> getAllCouponsByGiver(String email) {
        User user = userService.getUserByEmail(email);
        return couponRepository.findAllByGiver(user).stream()
                .map(CouponResponse.CouponDTO::new)
                .toList();
    }
    public List<CouponResponse.CouponDTO> getAllCouponsByReceiver(String email) {
        User user = userService.getUserByEmail(email);
        return couponRepository.findAllByReceiver(user).stream()
                .map(CouponResponse.CouponDTO::new)
                .toList();
    }
    public CouponResponse.CouponDTO getTaggedCoupon(UUID couponUuid){
        return new CouponResponse.CouponDTO(getCouponByCouponUUid(couponUuid));
    }

    public List<CouponAdminResponse.CouponAdminDTO> getAllCoupons() {
        return couponRepository.findAll().stream()
            .map(CouponAdminResponse.CouponAdminDTO::new)
            .toList();
    }
    public CouponAdminResponse.CouponAdminDTO getTaggedCouponAdmin(String couponNumber) {
        return new CouponAdminResponse.CouponAdminDTO(getCouponByCouponNumber(couponNumber));
    }

    public Coupon getCouponByCouponNumber(String couponNumber) {
        return couponRepository.findByCouponNumber(couponNumber)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessage.COUPON_NOT_FOUND));
    }
    private Coupon getCouponByCouponUUid(UUID couponUuid) {
        return couponRepository.findById(couponUuid)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessage.COUPON_NOT_FOUND));
    }
}
