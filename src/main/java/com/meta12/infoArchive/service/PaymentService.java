package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.PaymentConfirmRequestDto;
import com.meta12.infoArchive.entity.Purchase;
import com.meta12.infoArchive.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PurchaseService purchaseService;
    public void confirmPayment(PaymentConfirmRequestDto dto) {

        if (dto.getOrderId() == null || dto.getOrderId().trim().isEmpty()) {
            throw new IllegalArgumentException("주문번호가 없습니다.");
        }

        if (dto.getAmount() == null || dto.getAmount() <= 0) {
            throw new IllegalArgumentException("결제 금액이 올바르지 않습니다.");
        }

        if (dto.getPaymentMethod() == null || dto.getPaymentMethod().trim().isEmpty()) {
            throw new IllegalArgumentException("결제 수단이 선택되지 않았습니다.");
        }

        // 카드 결제일 경우 입력값이 비어있는지만 확인
        if ("CARD".equals(dto.getPaymentMethod())) {
            validateMockCard(dto);
        }

        System.out.println("===== MOCK 멤버십 결제 승인 =====");
        System.out.println("주문번호 = " + dto.getOrderId());
        System.out.println("결제금액 = " + dto.getAmount());
        System.out.println("결제수단 = " + dto.getPaymentMethod());
        System.out.println("멤버십 타입 = " + dto.getMembershipType());
        System.out.println("카드번호 = " + maskCardNumber(dto.getCardNumber()));
        System.out.println("결제상태 = APPROVED");

        Purchase purchase = new Purchase();

        purchaseService.save(purchase);
    }

    private void validateMockCard(PaymentConfirmRequestDto dto) {

        if (dto.getCardNumber() == null || dto.getCardNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("카드번호를 입력해주세요.");
        }

        if (dto.getCardExpire() == null || dto.getCardExpire().trim().isEmpty()) {
            throw new IllegalArgumentException("유효기간을 입력해주세요.");
        }

        if (dto.getCardCvc() == null || dto.getCardCvc().trim().isEmpty()) {
            throw new IllegalArgumentException("CVC를 입력해주세요.");
        }

        if (dto.getCardPassword() == null || dto.getCardPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("카드 비밀번호 앞 2자리를 입력해주세요.");
        }

        // 여기서는 실제 카드번호 검증 안 함
        // 아무 카드번호나 입력해도 승인
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            return "-";
        }

        String clean = cardNumber.replace("-", "").replace(" ", "");

        if (clean.length() < 4) {
            return "****";
        }

        return "****-****-****-" + clean.substring(clean.length() - 4);
    }
}