package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.PaymentConfirmRequestDto;
import com.meta12.infoArchive.service.PaymentService;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;

    // 결제 페이지
    @GetMapping("/payment/checkout")
    public String checkoutPage(Model model) {

        String orderId = "ORDER-" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        int amount = 239000;

        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", amount);
        model.addAttribute("productName", "엣지에서 태어나는 삽화스킬 AtoZ");

        return "payment/checkout";
    }

    // 결제 처리
    @PostMapping("/payment/confirm")
    public String confirmPayment(
            PaymentConfirmRequestDto dto,
            Authentication authentication
    ) {
        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        paymentService.confirmPayment(dto, authentication);

        if ("MONTHLY".equals(dto.getMembershipType())) {
            userService.activateMembership(authentication);
        }

        return "redirect:/payment/payment-complete";
    }

    @GetMapping("/payment/payment-complete")
    public String paymentCompletePage() {
        return "payment/payment-complete";
    }
}