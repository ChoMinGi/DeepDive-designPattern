package net.dayner.api.domain.paymentArchive.strategy;

import net.dayner.api.domain.paymentArchive.entity.PaymentArchive;

public interface PaymentArchiveStrategy<T> {
    Class<T> getSupportedType();
    PaymentArchive savePaymentRecord(T transaction, String phoneNumber);

}

