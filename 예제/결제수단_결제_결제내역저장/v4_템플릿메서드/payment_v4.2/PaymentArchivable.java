package net.dayner.api.domain.payment;

import net.dayner.api.domain.payment.paymentArchive.entity.PaymentArchive;

public interface PaymentArchivable {
    PaymentArchive convertToPaymentArchive(String phoneNumber, String description, int balance);
}
