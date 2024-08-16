package net.dayner.api.domain.payment.coupon.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.dayner.api.domain.payment.coupon.entity.Coupon;
import net.dayner.api.domain.user.entity.User;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
@ToString
public class CouponAdminResponse {
    private final CouponAdminDTO couponAdminDTO;

    @Getter
    @ToString
    public static class CouponAdminDTO extends CouponResponse.CouponDTO {
        private final String giverPhoneNumber;
        private final String receiverPhoneNumber;
        private final boolean isPrepaid;

        public CouponAdminDTO(Coupon coupon) {
            super(coupon);
            this.giverPhoneNumber = Optional.ofNullable(coupon.getGiver())
                    .map(User::getPhoneNumber)
                    .map(Object::toString)
                    .orElse(null);
            this.receiverPhoneNumber = Optional.ofNullable(coupon.getReceiver())
                    .map(User::getPhoneNumber)
                    .map(Object::toString)
                    .orElse(null);
            this.isPrepaid = coupon.isPrepaid();
        }
    }
}