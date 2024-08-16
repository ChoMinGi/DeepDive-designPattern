package net.dayner.api.domain.payment.coupon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.dayner.api.domain.payment.coupon.dto.CouponRequest;
import net.dayner.api.domain.payment.coupon.service.CouponCommandService;
import net.dayner.api.domain.payment.coupon.service.CouponQueryService;
import net.dayner.api.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/coupons")
@Tag(name = "[Member] Coupon Management", description = "Coupon Management API for Members")
public class CouponManagementController {

    private final CouponCommandService couponCommandService;
    private final CouponQueryService couponQueryService;
    @GetMapping("/list/issued")
    @Operation(summary = "쿠폰 발행목록 조회", description = "회원이 발행한 쿠폰 목록을 조회합니다.")
    public ResponseEntity<?> getCouponListByGiver(@AuthenticationPrincipal OAuth2User principal) {
        return ResponseEntity.ok(ApiUtils.success(couponQueryService.getAllCouponsByGiver(principal.getAttribute("email"))));
    }

    @GetMapping("/list/registered")
    @Operation(summary = "쿠폰 등록목록 조회", description = "회원이 등록한 쿠폰 목록을 조회합니다.")
    public ResponseEntity<?> getAllCouponsByReceiver(@AuthenticationPrincipal OAuth2User principal) {
        return ResponseEntity.ok(ApiUtils.success(couponQueryService.getAllCouponsByReceiver(principal.getAttribute("email"))));
    }

    @PostMapping("/registration")
    @Operation(summary = "쿠폰 등록", description = "회원이 쿠폰를 등록합니다.") //TODO : 등록한 회원과 구매회원이 다른 경우 선물~ 관련 알림 컨텐츠
    public ResponseEntity<?> registrationCoupon(@Valid  @RequestBody CouponRequest.CouponRegistrationDTO couponRegistrationDTO, @AuthenticationPrincipal OAuth2User principal) {
        return ResponseEntity.ok(ApiUtils.success(couponCommandService.registrationCoupon(couponRegistrationDTO, principal.getAttribute("email"))));
    }
}