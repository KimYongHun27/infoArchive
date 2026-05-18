package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.PaymentConfirmRequestDto;
import com.meta12.infoArchive.entity.Payment;
import com.meta12.infoArchive.entity.PaymentStatus;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserService userService;

    public void confirmPayment(PaymentConfirmRequestDto dto, Authentication authentication) {

        if (dto.getOrderId() == null || dto.getOrderId().trim().isEmpty()) {
            throw new IllegalArgumentException("주문번호가 없습니다.");
        }

        if (dto.getAmount() == null || dto.getAmount() <= 0) {
            throw new IllegalArgumentException("결제 금액이 올바르지 않습니다.");
        }

        if (dto.getPaymentMethod() == null || dto.getPaymentMethod().trim().isEmpty()) {
            throw new IllegalArgumentException("결제 수단이 선택되지 않았습니다.");
        }

        if ("CARD".equals(dto.getPaymentMethod())) {
            validateMockCard(dto);
        }

        if (!Boolean.TRUE.equals(dto.getAgreeTerms())) {
            throw new IllegalArgumentException("이용약관에 동의해야 결제할 수 있습니다.");
        }

        User user = userService.getLoginUser(authentication);

        System.out.println("===== MOCK 멤버십 결제 승인 =====");
        System.out.println("주문번호 = " + dto.getOrderId());
        System.out.println("결제금액 = " + dto.getAmount());
        System.out.println("결제수단 = " + dto.getPaymentMethod());
        System.out.println("멤버십 타입 = " + dto.getMembershipType());
        System.out.println("카드번호 = " + maskCardNumber(dto.getCardNumber()));
        System.out.println("결제상태 = COMPLETED");
        System.out.println("결제회원 = " + user.getEmail());

        Payment payment = new Payment();
        payment.setOrderNumber(dto.getOrderId());
        payment.setDiscountAmount(dto.getAmount());
        payment.setOrderDate(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        payment.setUser(user);

        paymentRepository.save(payment);
    }

    private void validateMockCard(PaymentConfirmRequestDto dto) {

        String cardNumber = onlyNumber(dto.getCardNumber());
        String cardExpire = onlyNumber(dto.getCardExpire());
        String cardCvc = onlyNumber(dto.getCardCvc());
        String cardPassword = onlyNumber(dto.getCardPassword());

        if (cardNumber.length() != 19) {
            throw new IllegalArgumentException("카드번호는 숫자 19자리로 입력해주세요.");
        }

        if (cardExpire.length() != 4) {
            throw new IllegalArgumentException("유효기간은 숫자 4자리로 입력해주세요.");
        }

        if (cardCvc.length() != 3) {
            throw new IllegalArgumentException("CVC는 숫자 3자리로 입력해주세요.");
        }

        if (cardPassword.length() != 2) {
            throw new IllegalArgumentException("카드 비밀번호는 앞 2자리만 입력해주세요.");
        }

        if (!Boolean.TRUE.equals(dto.getAgreeTerms())) {
            throw new IllegalArgumentException("이용약관에 동의해야 결제할 수 있습니다.");
        }
    }

    private String onlyNumber(String value) {
        if (value == null) {
            return "";
        }

        return value.replaceAll("[^0-9]", "");
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