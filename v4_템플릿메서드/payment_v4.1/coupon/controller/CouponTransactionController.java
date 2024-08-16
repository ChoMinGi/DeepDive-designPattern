package net.dayner.api.domain.payment.coupon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.dayner.api.domain.payment.coupon.service.CouponQueryService;
import net.dayner.api.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coupons")
@Tag(name = "[NonMember] Coupon Transaction", description = "Coupon Transaction API for Non-Members")
public class CouponTransactionController {
    private final CouponQueryService couponQueryService;

    @GetMapping("/{couponUuid}")
    @Operation(summary = "쿠폰 단일 조회", description = "유저레벨에서의 쿠폰을 조회합니다.")
    public ResponseEntity<?> getCouponTransactionHistory(@PathVariable UUID couponUuid){
        return ResponseEntity.ok(ApiUtils.success(couponQueryService.getTaggedCoupon(couponUuid)));
    }
}