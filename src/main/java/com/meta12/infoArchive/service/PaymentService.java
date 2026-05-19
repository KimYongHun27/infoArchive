package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.PaymentConfirmRequestDto;
import com.meta12.infoArchive.entity.Payment;
import com.meta12.infoArchive.entity.PaymentStatus;
import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserService userService;
    private final ProductService productService;

    public void confirmPayment(PaymentConfirmRequestDto dto, Authentication authentication) {

        User user = userService.getLoginUser(authentication);

        if (dto.getOrderId() == null || dto.getOrderId().trim().isEmpty()) {
            throw new IllegalArgumentException("주문번호가 없습니다.");
        }

        if (dto.getAmount() == null || dto.getAmount() <= 0) {
            throw new IllegalArgumentException("결제 금액이 올바르지 않습니다.");
        }

        if (dto.getPaymentMethod() == null || dto.getPaymentMethod().trim().isEmpty()) {
            throw new IllegalArgumentException("결제 수단이 선택되지 않았습니다.");
        }

        if (!Boolean.TRUE.equals(dto.getAgreeTerms())) {
            throw new IllegalArgumentException("이용약관에 동의해야 결제할 수 있습니다.");
        }

        if ("CARD".equals(dto.getPaymentMethod())) {
            validateMockCard(dto);
        }

        /*
         * 멤버십 결제
         * - 이미 멤버십 활성화 상태면 payment에 또 저장하지 않음
         */
        if ("MONTHLY".equals(dto.getMembershipType())) {

            if (userService.isMembershipActive(user)) {
                throw new IllegalArgumentException("이미 멤버십을 이용 중입니다.");
            }

            Payment payment = new Payment();
            payment.setOrderNumber(dto.getOrderId());
            payment.setDiscountAmount(0); // 멤버십은 주문내역 금액 노출 방지용
            payment.setOrderDate(LocalDateTime.now());
            payment.setPaymentStatus(PaymentStatus.COMPLETED);
            payment.setUser(user);
            payment.setProduct(null);

            paymentRepository.save(payment);

            return;
        }

        /*
         * 강의 결제
         */
        Product product = productService.getProduct(dto.getProductId());

        if (product == null) {
            throw new IllegalArgumentException("결제할 상품을 찾을 수 없습니다.");
        }

        Payment payment = new Payment();
        payment.setOrderNumber(dto.getOrderId());
        payment.setDiscountAmount(dto.getAmount());
        payment.setOrderDate(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        payment.setUser(user);
        payment.setProduct(product);

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