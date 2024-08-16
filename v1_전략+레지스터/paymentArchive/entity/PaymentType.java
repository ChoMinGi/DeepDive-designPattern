package net.dayner.api.domain.paymentArchive.entity;

import lombok.Getter;
import net.dayner.api.exception.ErrorMessage;

import java.util.Arrays;

@Getter
public enum PaymentType {
    GIFT_CARD("기프트 카드"),
    COUPON("쿠폰");

    private final String title;

    PaymentType(String title){this.title =title;}

    public static PaymentType fromTitle(String title){
        return Arrays.stream(PaymentType.values())
                .filter(enumValue -> enumValue.getTitle().equals(title))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.PAYMENT_TYPE_NAME_ERROR +title));
    }
}
