package net.dayner.api.domain.payment.coupon.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import net.dayner.api.exception.ValidationMessage;
import net.dayner.api.serializer.CustomBooleanDeserializer;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Getter
@Builder
@ToString
public class CouponAdminRequest {
    private final CouponIssueDTO couponIssueDTO;
    private final CouponUsageDTO couponUsageDTO;
//    private final CouponRedeemDTO couponRedeemDTO;
    private final CouponsImageUpdateDTO couponsImageUpdateDTO;

    public record CouponIssueDTO(
            @Min(value = 1, message = ValidationMessage.ISSUE_AMOUNT_IS_POSITIVE_NUMBER) int quantity,
            @Pattern(regexp = "^[0-9]{11}$", message = ValidationMessage.PHONE_NUMBER_DIGIT_IS_POSITIVE_EIGHT_NUMBER) String giverPhoneNumber,
            String description, String giverNickname,
            @Min(value = 1, message = ValidationMessage.EXPIRY_MONTHS_IS_LARGER_THAN_ONE) int expiryMonths,
            @Min(value = 0, message = ValidationMessage.CHARGE_BALANCE_IS_POSITIVE_NUMBER) int maxUsageAmount,
            @JsonDeserialize(using = CustomBooleanDeserializer.class) boolean isPrepaid

    ){
    }

    public record CouponUsageDTO(
            @Pattern(regexp = "^[0-9]{5}$", message = ValidationMessage.PROMOTIONAL_NUMBER_DIGIT_IS_FIVE) String couponNumber,
            @Min(value = 0, message = ValidationMessage.CHARGE_BALANCE_IS_POSITIVE_NUMBER) int usageAmount,
            String phoneNumber
    ){
    }

    public record CouponsImageUpdateDTO(
            List<String> couponNumberList, //TODO: 패턴 검증을 넣을지 고민중
            @URL(protocol = "http")
            String newImageUrl
    ){}

}
