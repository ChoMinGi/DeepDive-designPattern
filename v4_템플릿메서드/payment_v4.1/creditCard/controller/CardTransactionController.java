package net.dayner.api.domain.payment.creditCard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.dayner.api.domain.payment.creditCard.service.GiftCardQueryService;
import net.dayner.api.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/giftcard")
@Tag(name = "[NonMember] Card Transaction", description = "Card Transaction API for Non-Members")
public class CardTransactionController {
    private final GiftCardQueryService giftCardQueryService;

    @GetMapping("/info/{cardUuid}")
    @Operation(summary = "카드 거래 내역 조회", description = "비회원이 카드 잔액 및 사용내역을 조회합니다.")
    public ResponseEntity<?> getCardTransactionHistory(@PathVariable UUID cardUuid){
        return ResponseEntity.ok(ApiUtils.success(giftCardQueryService.getTaggedCardSimpleHistoryList(cardUuid)));
    }
    @GetMapping("/info1/{cardUuid}")
    @Operation(summary = "카드 거래 내역 조회", description = "비회원이 카드 잔액 및 사용내역을 조회합니다.")
    public ResponseEntity<?> getCardTransactionSimHistory(@PathVariable UUID cardUuid){
        return ResponseEntity.ok(ApiUtils.success(giftCardQueryService.getTaggedCardSimpleSimpleHistoryList(cardUuid)));
    }
    

}