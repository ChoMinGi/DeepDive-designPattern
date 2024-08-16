package net.dayner.api.domain.payment.creditCard.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.dayner.api.domain.payment.creditCard.entity.GiftCard;
import net.dayner.api.domain.payment.creditCard.entity.GiftCardTransaction;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class GiftCardResponse {
    private final GiftCardHistoryDTO giftCardHistoryDTO;
    private final GiftCardHistoryListDTO giftCardHistoryListDTO;
    private final GiftCardDTO giftCardDTO;

    @Getter
    @ToString
    public static class GiftCardDTO{
        private final String imageUrl;
        private final String cardNumber;
        private final int balance;
        private final String status;
        @JsonFormat(pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
        private final LocalDateTime expiryDate;

        public GiftCardDTO(GiftCard giftCard){
            this.imageUrl = giftCard.getImageUrl();
            this.cardNumber = giftCard.getCardNumber();
            this.balance = giftCard.getBalance();
            this.status = giftCard.getStatus().getTitle();
            this.expiryDate = giftCard.getExpiryDate();
        }
    }


    @Getter
    @ToString
    public static class GiftCardHistoryDTO{
        private final String transactionType;
        @JsonFormat(pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
        private final LocalDateTime transactionDate;
        private final int amount;
        public GiftCardHistoryDTO(GiftCardTransaction giftCardTransaction) {
            this.transactionType = giftCardTransaction.getTransactionType().getTitle();
            this.transactionDate = giftCardTransaction.getTransactionDate();
            this.amount = giftCardTransaction.getAmount();
        }
    }
    @Getter
    @ToString
    public static class GiftCardHistoryListDTO {
        private final String imageUrl;
        private final String cardNumber;
        @JsonFormat(pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
        private final LocalDateTime expiryDate;
        private final int balance;
        private final String status;
        private final List<GiftCardHistoryDTO> giftCardHistoryDTOList;
        public GiftCardHistoryListDTO(List<GiftCardTransaction> giftCardTransactionList) {
            this.imageUrl = giftCardTransactionList.get(0).getGiftCard().getImageUrl();
            this.cardNumber = giftCardTransactionList.get(0).getGiftCard().getCardNumber();
            this.expiryDate = giftCardTransactionList.get(0).getGiftCard().getExpiryDate();
            this.balance = giftCardTransactionList.get(0).getGiftCard().getBalance();
            this.status = giftCardTransactionList.get(0).getGiftCard().getStatus().getTitle();
            this.giftCardHistoryDTOList = giftCardTransactionList.stream()
                    .map(GiftCardResponse.GiftCardHistoryDTO::new)
                    .toList();
        }
        public GiftCardHistoryListDTO(GiftCard giftCard){
            this.status = giftCard.getStatus().getTitle();
            this.cardNumber = giftCard.getCardNumber();
            this.imageUrl = null;
            this.expiryDate = null;
            this.balance = 0;
            this.giftCardHistoryDTOList = null;
        }
    }

    @Getter
    @ToString
    public static class GiftCardHistorySimpleListDTO {
        private final String imageUrl;
        private final String cardNumber;
        @JsonFormat(pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
        private final LocalDateTime expiryDate;
        private final int balance;
        private final String status;
        private final List<GiftCardHistoryDTO> giftCardHistoryDTOList;
        public GiftCardHistorySimpleListDTO(GiftCard giftCard, List<GiftCardTransaction> giftCardTransactionList) {
            this.imageUrl = giftCard.getImageUrl();
            this.cardNumber = giftCard.getCardNumber();
            this.expiryDate = giftCard.getExpiryDate();
            this.balance = giftCard.getBalance();
            this.status = giftCard.getStatus().getTitle();
            this.giftCardHistoryDTOList = giftCardTransactionList.stream()
                    .map(GiftCardResponse.GiftCardHistoryDTO::new)
                    .toList();
        }
        public GiftCardHistorySimpleListDTO(GiftCard giftCard){
            this.status = giftCard.getStatus().getTitle();
            this.cardNumber = giftCard.getCardNumber();
            this.imageUrl = null;
            this.expiryDate = null;
            this.balance = 0;
            this.giftCardHistoryDTOList = null;
        }
    }
}
