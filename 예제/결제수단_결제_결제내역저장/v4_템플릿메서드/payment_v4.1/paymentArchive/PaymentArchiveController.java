package net.dayner.api.domain.payment.paymentArchive;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/paymentArchive")
@Tag(name = "[Admin] View paymentArchive", description = "PaymentArchive API for Admins")
public class PaymentArchiveController {

    private final PaymentArchiveService paymentArchiveService;


}
