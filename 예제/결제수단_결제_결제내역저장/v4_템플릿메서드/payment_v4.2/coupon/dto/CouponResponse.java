package net.dayner.api.domain.payment.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.dayner.api.domain.payment.coupon.entity.Coupon;

import java.time.LocalDateTime;

@Getter
@ToString
@RequiredArgsConstructor
public class CouponResponse {
    private final CouponDTO couponDTO;

    @Getter
    @ToString
    public static class CouponDTO{
        private final String imageUrl;
        private final String couponNumber;
        private final boolean isUsed;
        @JsonFormat(pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
        private final LocalDateTime expiryDate;
        @JsonFormat(pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
        private final LocalDateTime usedDate;
        private final int maxUsageAmount;
        private final int usageAmount;
        private final String description;
        private final String giverNickname;

        public CouponDTO(Coupon coupon){
            this.imageUrl = coupon.getImageUrl();
            this.couponNumber=coupon.getCouponNumber();
            this.isUsed = coupon.isUsed();
            this.expiryDate = coupon.getExpiryDate();
            this.usedDate = coupon.getUsedDate();
            this.maxUsageAmount=coupon.getMaxUsageAmount();
            this.usageAmount = coupon.getUsageAmount();
            this.description = coupon.getDescription();
            this.giverNickname = coupon.getGiverNickname();
        }
    }
}
