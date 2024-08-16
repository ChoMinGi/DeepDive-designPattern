package net.dayner.api.domain.payment.creditCard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.dayner.api.domain.payment.creditCard.dto.GiftCardRequest;
import net.dayner.api.domain.payment.creditCard.service.GiftCardQueryService;
import net.dayner.api.domain.payment.creditCard.service.GiftCardCommandService;
import net.dayner.api.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/giftcard")
@Tag(name = "[Member] Gift Card Management", description = "Gift Card Management API for Members")
public class GiftCardManagementController {

    private final GiftCardCommandService giftCardCommandService;
    private final GiftCardQueryService giftCardQueryService;

    @GetMapping("/list")
    @Operation(summary = "기프트 카드 목록 조회", description = "회원이 등록한 기프트 카드 목록을 조회합니다.")
    public ResponseEntity<?> getGiftCardList(@AuthenticationPrincipal OAuth2User principal) {
        return ResponseEntity.ok(ApiUtils.success(giftCardQueryService.getAllGiftCardsByUser(principal.getAttribute("email"))));
    }

    @PostMapping("/registration")
    @Operation(summary = "기프트 카드 등록", description = "회원이 기프트 카드를 등록합니다.") //TODO : 등록한 회원과 구매회원이 다른 경우 선물~ 관련 알림 컨텐츠
    public ResponseEntity<?> registrationGiftCard(@Valid @RequestBody GiftCardRequest.GiftCardRegistrationDTO giftCardRegistrationDTO, @AuthenticationPrincipal OAuth2User principal) {
        return ResponseEntity.ok(ApiUtils.success(giftCardCommandService.registrationGiftCard(giftCardRegistrationDTO, principal.getAttribute("email"))));
    }
}

