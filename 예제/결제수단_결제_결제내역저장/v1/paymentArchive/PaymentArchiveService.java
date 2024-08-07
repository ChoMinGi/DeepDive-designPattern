package net.dayner.api.domain.paymentArchive;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.dayner.api.domain.coupon.entity.Coupon;
import net.dayner.api.domain.creditCard.entity.GiftCardTransaction;
import net.dayner.api.domain.paymentArchive.entity.PaymentArchive;
import net.dayner.api.domain.paymentArchive.strategy.PaymentArchiveStrategy;
import net.dayner.api.exception.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentArchiveService {
    private final PaymentArchiveRepository paymentArchiveRepository;
    private final Map<Class<?>, PaymentArchiveStrategy<?>> strategyMap;

    // 전략을 의존성 주입을 통해 받아옵니다.
    @Autowired
    public PaymentArchiveService(PaymentArchiveRepository paymentArchiveRepository, List<PaymentArchiveStrategy<?>> strategies) {
        this.paymentArchiveRepository = paymentArchiveRepository;
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(PaymentArchiveStrategy::getSupportedType, Function.identity()));
    }

    @Transactional
    public void savePaymentArchiveFromGiftCard(List<GiftCardTransaction> giftCardTransactionList, String phoneNumber) {
        PaymentArchiveStrategy<GiftCardTransaction> strategy = getStrategy(GiftCardTransaction.class);

        List<PaymentArchive> archives = giftCardTransactionList.stream()
                .map(transaction -> strategy.savePaymentRecord(transaction, phoneNumber))
                .collect(Collectors.toList());

        paymentArchiveRepository.saveAll(archives);
    }

    @Transactional
    public void savePaymentArchiveFromCoupon(Coupon coupon, String phoneNumber) {
        PaymentArchiveStrategy<Coupon> strategy = getStrategy(Coupon.class);

        PaymentArchive archive = strategy.savePaymentRecord(coupon, phoneNumber);
        paymentArchiveRepository.save(archive);
    }
    private <T> PaymentArchiveStrategy<T> getStrategy(Class<T> clazz) {
        PaymentArchiveStrategy<?> strategy = strategyMap.get(clazz);
        if (strategy == null) {
            throw new IllegalStateException(ErrorMessage.PAYMENT_ARCHIVE_STRATEGY_ERROR + clazz.getSimpleName());
        }
        try {
            return (PaymentArchiveStrategy<T>) strategy;
        } catch (ClassCastException e) {
            throw new IllegalStateException("Strategy found but could not be cast to the correct type: " + clazz.getSimpleName(), e);
        }
    }
}
