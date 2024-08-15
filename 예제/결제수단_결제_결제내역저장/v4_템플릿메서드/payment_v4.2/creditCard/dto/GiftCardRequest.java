package net.dayner.api.domain.payment.creditCard.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import net.dayner.api.exception.ValidationMessage;

@Getter
@Builder
@ToString
public class GiftCardRequest {
    private final GiftCardRegistrationDTO giftCardRegistrationDTO;

    public record GiftCardRegistrationDTO(
        @Pattern(regexp = "^[0-9]{5}$", message = ValidationMessage.PROMOTIONAL_NUMBER_DIGIT_IS_FIVE) String cardNumber
        ) {}
}
