package net.dayner.api.domain.payment.creditCard.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.dayner.api.domain.payment.creditCard.dto.GiftCardAdminRequest;
import net.dayner.api.domain.payment.creditCard.entity.GiftCard;
import net.dayner.api.domain.payment.creditCard.service.GiftCardCommandService;
import net.dayner.api.domain.payment.creditCard.service.GiftCardQueryService;
import net.dayner.api.domain.payment.paymentArchive.PaymentArchiveService;
import net.dayner.api.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/giftcard")
@Tag(name = "[Admin] Card Tagging", description = "Card Tagging API for Admins")
public class GiftCardAdminController {

    private final GiftCardQueryService giftCardQueryService;
    private final GiftCardCommandService giftCardCommandService;
    private final PaymentArchiveService paymentArchiveService;

    @GetMapping("/{cardNumber}")
    @Operation(summary = "카드 거래 내역 조회", description = "카드 잔액 및 사용내역을 조회합니다.")
    public ResponseEntity<?> getCardTransactionAdminHistory(@PathVariable String cardNumber){
        return ResponseEntity.ok(ApiUtils.success(giftCardQueryService.getTaggedCardAdminHistoryList(cardNumber)));
    }
    @PostMapping("/{cardNumber}")
    @Operation(summary = "카드 태깅 및 관리", description = "관리자가 카드를 태그하고 해당 카드의 잔액 조회, 변경, 활성화 상태를 관리합니다.")
    public ResponseEntity<?> manageTaggedCard(@Valid @RequestBody GiftCardAdminRequest.GiftCardTransactionDTO giftCardTransactionDTO) {
        paymentArchiveService.savePaymentArchive(giftCardCommandService.transactGiftCard(giftCardTransactionDTO).convertToPaymentArchive(giftCardTransactionDTO.phoneNumber(), giftCardTransactionDTO.transactionType(), giftCardTransactionDTO.amount()));
        return ResponseEntity.ok(ApiUtils.success(giftCardCommandService.transactGiftCard(giftCardTransactionDTO)));
    }
    @PostMapping("/{cardNumber}/reset")
    @Operation(summary = "카드 리셋", description = "관리자가 카드를 태그하고 해당 카드를 리셋합니다. 기존 거래 정보는 저장됩니다.")
    public ResponseEntity<?> resetCoupon(@PathVariable String cardNumber) {
        return ResponseEntity.ok(ApiUtils.success(giftCardCommandService.resetCard(cardNumber)));
    }
    @PostMapping("/issue")
    @Operation(summary = "카드 발급", description = "관리자가 기프트카드를 빈 RFID 카드에 추가하기 위해 quantity 개의 랜덤 uuid 와 카드 번호를 생성합니다.")
    public ResponseEntity<?> createGiftCard(@Valid @RequestBody GiftCardAdminRequest.GiftCardIssueDTO giftCardIssueDTO) {
        List<GiftCard> newGiftCard = giftCardCommandService.createGiftCards(Integer.parseInt(giftCardIssueDTO.quantity()));
        return ResponseEntity.ok(ApiUtils.success(newGiftCard));
    }
    @PostMapping("/activate")
    @Operation(summary = "카드 판매시 활성화", description = "관리자가 기프트카드의 카드 번호와 금액 그리고 구입한 회원의 전화번호 리스트를 등록하여 활성화를 합니다")
    public ResponseEntity<?> activateSingleGiftCard(@Valid @RequestBody GiftCardAdminRequest.GiftCardActiveDTO giftCardActiveDTO) {
        return ResponseEntity.ok(ApiUtils.success(giftCardCommandService.activateSingleGiftCard(giftCardActiveDTO)));
    }

    @PostMapping("/bulk-activate")
    @Operation(summary = "카드 판매시 다중 활성화", description = "관리자가 기프트카드의 카드 번호와 금액 그리고 구입한 회원의 전화번호 리스트를 등록하여 활성화를 합니다")
    public ResponseEntity<?> activateSingleGiftCard(@Valid @RequestBody List<GiftCardAdminRequest.GiftCardActiveDTO> giftCardActiveDTOList) {
        return ResponseEntity.ok(ApiUtils.success(giftCardCommandService.activateMultiGiftCard(giftCardActiveDTOList)));
    }

    @GetMapping("/list")
    @Operation(summary = "기프트 카드 전체 현황 조회", description = "등록된 및 미등록 기프트 카드 목록과 잔액 상황을 조회합니다.")
    public ResponseEntity<?> getGiftCardOverview() {
        return ResponseEntity.ok(ApiUtils.success(giftCardQueryService.getAllGiftCards()));
    }

    @GetMapping("/details/{cardNumber}")
    @Operation(summary = "특정 기프트 카드 상세 정보 조회", description = "선택된 기프트 카드의 상세 정보를 조회합니다.")
    public ResponseEntity<?> getGiftCardDetails(@PathVariable String cardNumber) {
        return ResponseEntity.ok(ApiUtils.success(giftCardQueryService.getTaggedCard(cardNumber)));
    }

    @PatchMapping("/image")
    @Operation(summary = "기프트카드 이미지 개별 변경", description = "등록된 및 기프트카드의 이미지를 변경합니다..")
    public ResponseEntity<?> changeCouponsImage(@Valid @RequestBody GiftCardAdminRequest.GiftCardImageUpdateDTO giftCardImageUpdateDTO) {
        giftCardCommandService.updateGiftCardImage(giftCardImageUpdateDTO);
        return ResponseEntity.ok(ApiUtils.success(giftCardImageUpdateDTO.newImageUrl()));
    }


}