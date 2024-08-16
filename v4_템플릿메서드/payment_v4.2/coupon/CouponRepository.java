package net.dayner.api.domain.payment.coupon;

import net.dayner.api.domain.payment.coupon.entity.Coupon;
import net.dayner.api.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {

    List<Coupon> findAllByGiver(User user);
    List<Coupon> findAllByReceiver(User user);
    Optional<Coupon> findByCouponNumber(String couponNumber);
    Optional<Coupon> findById(UUID couponUuid);
    boolean existsByCouponNumber(String couponNumber);
    List<Coupon> findByCouponNumberIn(List<String> couponNumberList);
}
