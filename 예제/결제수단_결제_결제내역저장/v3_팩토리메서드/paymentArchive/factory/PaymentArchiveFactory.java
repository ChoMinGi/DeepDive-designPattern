package net.dayner.api.domain.paymentArchive.factory;

import net.dayner.api.domain.paymentArchive.entity.PaymentArchive;

public interface PaymentArchiveFactory<T> {
    PaymentArchive convertToPaymentArchive(T transaction, String phoneNumber);

}
