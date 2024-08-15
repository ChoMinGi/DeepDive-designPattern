package net.dayner.api.domain.payment.creditCard.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.dayner.api.domain.payment.creditCard.entity.GiftCard;
import net.dayner.api.domain.payment.creditCard.entity.GiftCardTransaction;
import net.dayner.api.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
@ToString
public class GiftCardAdminResponse {
    private final GiftCardHistoryListAdminDTO giftCardHistoryListAdminDTO;
    private final GiftCardAdminDTO giftCardAdminDTO;

    @Getter
    @ToString
    public static class GiftCardAdminDTO extends GiftCardResponse.GiftCardDTO {
        private final String buyer;
        private final String userPhoneNumber;
        public GiftCardAdminDTO(GiftCard giftCard){
            super(giftCard);
            this.buyer = giftCard.getBuyer();
            this.userPhoneNumber = Optional.ofNullable(giftCard.getUser())
                    .map(User::getPhoneNumber)
                    .map(Object::toString)
                    .orElse(null);
        }
    }

    @Getter
    @ToString
    public static class GiftCardHistoryListAdminDTO extends GiftCardResponse.GiftCardHistoryListDTO {
        private final String userPhoneNumber;
        private final String buyer;
        public GiftCardHistoryListAdminDTO(List<GiftCardTransaction> giftCardTransactionList) {
            super(giftCardTransactionList);
            this.buyer = giftCardTransactionList.get(0).getGiftCard().getBuyer();
            this.userPhoneNumber = Optional.ofNullable(giftCardTransactionList.get(0).getGiftCard().getUser())
                    .map(User::getPhoneNumber)
                    .map(Object::toString)
                    .orElse(null);
        }
    }
}
