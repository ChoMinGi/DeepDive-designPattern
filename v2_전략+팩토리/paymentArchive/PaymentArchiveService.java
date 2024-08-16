package net.dayner.api.domain.paymentArchive;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentArchiveService {
    private final PaymentArchiveRepository paymentArchiveRepository;

    // PaymentArchive의 Read 만 담당.
}
