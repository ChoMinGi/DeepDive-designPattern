package net.dayner.api.domain.payment.paymentArchive;

import lombok.RequiredArgsConstructor;
import net.dayner.api.domain.payment.paymentArchive.entity.PaymentArchive;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentArchiveService {
    private final PaymentArchiveRepository paymentArchiveRepository;
    public void savePaymentArchive(PaymentArchive paymentArchive){
        paymentArchiveRepository.save(paymentArchive);
    }

    // PaymentArchive 의 Read
}
