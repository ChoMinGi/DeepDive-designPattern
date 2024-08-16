package net.dayner.api.domain.payment.creditCard.repository;

import net.dayner.api.domain.payment.creditCard.entity.GiftCard;
import net.dayner.api.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GiftCardRepository extends JpaRepository<GiftCard,Long> {
    List<GiftCard> findAllByUser(User user);
    Optional<GiftCard> findByCardNumber(String cardNumber);
    Optional<GiftCard> findById(UUID cardUuid);
    boolean existsByCardNumber(String cardNumber);
}
