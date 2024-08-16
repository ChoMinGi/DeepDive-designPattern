package net.dayner.api.domain.payment.creditCard.repository;

import net.dayner.api.domain.payment.creditCard.entity.GiftCard;
import net.dayner.api.domain.payment.creditCard.entity.GiftCardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GiftCardTransactionRepository extends JpaRepository<GiftCardTransaction,Long> {
    @Query("SELECT gct FROM GiftCardTransaction gct JOIN gct.giftCard gc WHERE gc.cardNumber = :cardNumber ORDER BY gct.transactionDate DESC")
    List<GiftCardTransaction> findAllTransactionsByCardNumber(@Param("cardNumber") String cardNumber);
    @Query("SELECT gct FROM GiftCardTransaction gct JOIN gct.giftCard gc WHERE gc.id = :cardUuid  ORDER BY gct.transactionDate DESC")
    List<GiftCardTransaction> findAllTransactionsByGiftCardId(@Param("cardUuid") UUID cardUuid);
    List<GiftCardTransaction> findByGiftCardOrderByTransactionDateDesc(GiftCard giftCard);
}
