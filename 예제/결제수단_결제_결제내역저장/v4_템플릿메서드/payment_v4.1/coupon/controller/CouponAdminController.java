package net.dayner.api.domain.payment.coupon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.dayner.api.domain.payment.coupon.dto.CouponAdminRequest;
import net.dayner.api.domain.payment.coupon.entity.Coupon;
import net.dayner.api.domain.payment.coupon.service.CouponCommandService;
import net.dayner.api.domain.payment.coupon.service.CouponQueryService;
import net.dayner.api.domain.payment.paymentArchive.PaymentArchiveService;
import net.dayner.api.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/coupons")
@Tag(name = "[Admin] Coupon Tagging", description = "Coupon Tagging API for Admins")
public class CouponAdminController {

    private final CouponQueryService couponQueryService;
    private final CouponCommandService couponCommandService;
    private final PaymentArchiveService paymentArchiveService;

    @GetMapping("")
    @Operation(summary = "쿠폰 전체 조회", description = "등록된 및 미등록 쿠폰 목록과 잔액 상황을 조회합니다.")
    public ResponseEntity<?> getCouponOverview() {
        return ResponseEntity.ok(ApiUtils.success(couponQueryService.getAllCoupons()));
    }
    @PostMapping("")
    @Operation(summary = "쿠폰 발급", description = "관리자가 기프트쿠폰를 빈 RFID 쿠폰에 추가하기 위해 quantity 개의 랜덤 uuid 와 쿠폰 번호를 생성합니다.")
    public ResponseEntity<?> createCoupon(@Valid @RequestBody CouponAdminRequest.CouponIssueDTO giftCouponIssueDTO) {
        List<Coupon> newCoupon = couponCommandService.createCoupons(giftCouponIssueDTO);
        return ResponseEntity.ok(ApiUtils.success(newCoupon));
    }
    @GetMapping("/{couponNumber}")
    @Operation(summary = "쿠폰 단일 조회", description = "관리자 레벨에서의 쿠폰을 조회합니다.")
    public ResponseEntity<?> getCouponTransactionAdminHistory(@PathVariable String couponNumber){
        return ResponseEntity.ok(ApiUtils.success(couponQueryService.getTaggedCouponAdmin(couponNumber)));
    }
    @PostMapping("/{couponNumber}")
    @Operation(summary = "쿠폰 태깅 및 관리", description = "관리자가 쿠폰를 태그하고 해당 쿠폰의 사용여부, 활성화 상태를 관리합니다.")
    public ResponseEntity<?> manageTaggedCoupon(@Valid @RequestBody CouponAdminRequest.CouponUsageDTO couponUsageDTO) {
        paymentArchiveService.savePaymentArchive(couponCommandService.usingCoupon(couponUsageDTO).convertToPaymentArchive(couponUsageDTO.phoneNumber()));
        return ResponseEntity.ok(ApiUtils.success("good"));
    }
    @PostMapping("/{couponNumber}/reset")
    @Operation(summary = "쿠폰 리셋", description = "관리자가 쿠폰를 태그하고 해당 쿠폰을 리셋합니다. 기존 거래 정보는 저장됩니다.")
    public ResponseEntity<?> resetCoupon(@PathVariable String couponNumber) {
        return ResponseEntity.ok(ApiUtils.success(couponCommandService.resetCoupon(couponNumber)));
    }
    @PatchMapping("/images")
    @Operation(summary = "쿠폰 이미지 단체 변경", description = "한번에 등록된 쿠폰의 이미지 여러개를 변경합니다..")
    public ResponseEntity<?> changeCouponsImage(@Valid @RequestBody CouponAdminRequest.CouponsImageUpdateDTO couponsImageUpdateDTO) {
        couponCommandService.updateCouponsImage(couponsImageUpdateDTO);
        return ResponseEntity.ok(ApiUtils.success(couponsImageUpdateDTO.newImageUrl()));
    }
    @PostMapping("/bulk-reset")
    @Operation(summary = "쿠폰 단체 리셋", description = "한번에 등록된 여러개의 쿠폰을 한번에 리셋합니다. 기존 거래 정보는 저장됩니다.")
    public ResponseEntity<?> resetBulkCoupon(@PathVariable String couponNumber) { //TODO
        return ResponseEntity.ok(ApiUtils.success(couponCommandService.resetCoupon(couponNumber)));
    }



}