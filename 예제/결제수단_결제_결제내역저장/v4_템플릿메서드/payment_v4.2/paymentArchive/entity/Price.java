package net.dayner.api.domain.payment.paymentArchive.entity;

import net.dayner.api.exception.ErrorMessage;

public class Price {
    private final int value;

    public Price(int value) {
        if (value < 0) {
            throw new IllegalArgumentException(ErrorMessage.PRICE_CANNOT_BE_NEGATIVE);
        }
        this.value = value;
    }

    // 차감
    public Price deduct(Price deduction) {
        int result = this.value - deduction.value;
        if (result < 0) {
            throw new IllegalArgumentException(ErrorMessage.PRICE_CANNOT_BE_NEGATIVE);
        }
        return new Price(result);
    }

    // 충전
    public Price charge(Price addition) {
        return new Price(this.value + addition.value);
    }

    // get
    public int getValue() {
        return value;
    }
}

