package net.dayner.api.domain.payment.creditCard.entity;

import lombok.Getter;
import net.dayner.api.exception.ErrorMessage;

import java.util.Arrays;

@Getter
public enum CardStatus {
    DEACTIVATE("비활성"),
    ACTIVE("활성");

    private final String title;

    CardStatus(String title){this.title =title;}

    public static CardStatus fromTitle(String title){
        return Arrays.stream(CardStatus.values())
                .filter(enumValue -> enumValue.getTitle().equals(title))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.CARD_STATUS_TYPE_NAME_ERROR +title));
    }
}
