package net.dayner.api.domain.payment.coupon.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import net.dayner.api.exception.ValidationMessage;

@Getter
@Builder
@ToString
public class CouponRequest {
    private final CouponRegistrationDTO couponRegistrationDTO;

    public record CouponRegistrationDTO(
            @Pattern(regexp = "^[0-9]{5}$", message = ValidationMessage.PROMOTIONAL_NUMBER_DIGIT_IS_FIVE) String couponNumber
    ) {}
}
