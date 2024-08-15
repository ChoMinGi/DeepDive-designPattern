package net.dayner.api.domain.paymentArchive;

import net.dayner.api.domain.paymentArchive.entity.PaymentArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentArchiveRepository extends JpaRepository<PaymentArchive, Long> {
}
