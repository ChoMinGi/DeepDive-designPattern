package net.dayner.api.domain.payment.creditCard.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import net.dayner.api.exception.ValidationMessage;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Getter
@Builder
@ToString
public class GiftCardAdminRequest {
    private final GiftCardIssueDTO giftCardIssueDTO;
    private final GiftCardTransactionDTO giftCardTransactionDTO;
    private final GiftCardActiveDTO giftCardActiveDTO;
    private final GiftCardsActiveDTO giftCardsActiveDTO;
    private final GiftCardImageUpdateDTO giftCardImageUpdateDTO;

    public record GiftCardIssueDTO(
            @Min(value = 1, message = ValidationMessage.ISSUE_AMOUNT_IS_POSITIVE_NUMBER) String quantity
    ){
    }
    public record GiftCardTransactionDTO(
        @Pattern(regexp = "^[0-9]{5}$", message = ValidationMessage.PROMOTIONAL_NUMBER_DIGIT_IS_FIVE) String cardNumber,
        @JsonProperty("transactionType") String transactionType,
        @Min(value = 0, message = ValidationMessage.PAY_BALANCE_IS_POSITIVE_NUMBER)
        int amount,
        @Pattern(regexp = "^[0-9]{11}$", message = ValidationMessage.PHONE_NUMBER_DIGIT_IS_POSITIVE_EIGHT_NUMBER) String phoneNumber
        ) {
    }
    public record GiftCardActiveDTO(
        @Pattern(regexp = "^[0-9]{5}$", message = ValidationMessage.PROMOTIONAL_NUMBER_DIGIT_IS_FIVE) String cardNumber,
        @Pattern(regexp = "^[0-9]{11}$", message = ValidationMessage.PHONE_NUMBER_DIGIT_IS_POSITIVE_EIGHT_NUMBER) String phoneNumber,
        @Min(value = 0, message = ValidationMessage.CHARGE_BALANCE_IS_POSITIVE_NUMBER) int balance

        ) {
    }
    public record GiftCardsActiveDTO(
            @Pattern(regexp = "^[0-9]{11}$", message = ValidationMessage.PHONE_NUMBER_DIGIT_IS_POSITIVE_EIGHT_NUMBER) String phoneNumber,
            @Min(value = 0, message = ValidationMessage.CHARGE_BALANCE_IS_POSITIVE_NUMBER) int balance,
            List<GiftCardNumberDTO> giftCardNumberDTOList

    ) {
        public record GiftCardNumberDTO(
            @Pattern(regexp = "^[0-9]{5}$", message = ValidationMessage.PROMOTIONAL_NUMBER_DIGIT_IS_FIVE)
            String cardNumber
        ) {}
    }

    public record GiftCardImageUpdateDTO(
            String giftCardNumber,
            @URL(protocol = "http")
            String newImageUrl
    ){}

}
