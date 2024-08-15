package net.dayner.api.domain.payment.creditCard.entity;

import lombok.Getter;
import net.dayner.api.exception.ErrorMessage;

import java.util.Arrays;
@Getter
public enum TransactionType {
    CHARGE("충전", 1),
    SPEND("사용", -1),
    CHARGE_CANCEL("충전 취소", -1),
    SPEND_CANCEL("사용 취소", 1),
    EXPIRATION("만료", -1),
    POINT_TRANSFER("포인트 전환", -1),
    EVENT("이벤트", 1);

    private final String title;
    private final int transactionSign; // 거래 방향을 나타내는 필드

    TransactionType(String title, int transactionSign) {
        this.title = title;
        this.transactionSign = transactionSign;
    }

    public static TransactionType fromTitle(String title) {
        return Arrays.stream(TransactionType.values())
                .filter(enumValue -> enumValue.getTitle().equals(title))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.CARD_STATUS_TYPE_NAME_ERROR + title));
    }

}

