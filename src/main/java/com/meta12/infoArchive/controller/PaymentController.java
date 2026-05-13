package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.PaymentConfirmRequestDto;
import com.meta12.infoArchive.service.PaymentService;
import com.meta12.infoArchive.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 페이지
    @GetMapping("/payment/checkout")
    public String checkoutPage() {
        return "payment/checkout";
    }

    // 결제 처리
    @PostMapping("/payment/confirm")
    public String confirmPayment(PaymentConfirmRequestDto dto) {

        System.out.println("===== 결제 요청 들어옴 =====");
        System.out.println("orderId = " + dto.getOrderId());
        System.out.println("amount = " + dto.getAmount());
        System.out.println("paymentMethod = " + dto.getPaymentMethod());
        System.out.println("membershipType = " + dto.getMembershipType());

        paymentService.confirmPayment(dto);

        return "redirect:/payment/payment-complete";
    }

    // 결제 완료 페이지
    @GetMapping("/payment/payment-complete")
    public String paymentCompletePage() {
        return "payment/payment-complete";
    }
}