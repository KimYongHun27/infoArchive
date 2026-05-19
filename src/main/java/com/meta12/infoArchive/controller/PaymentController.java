package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.PaymentConfirmRequestDto;
import com.meta12.infoArchive.service.PaymentService;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.service.EnrollmentService;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;
    private final EnrollmentService enrollmentService;

    // 결제 페이지
    @PostMapping("/payment/checkout") // 장바구니 폼이 POST이므로 PostMapping으로 변경하거나 처리 필요
    public String checkoutPage(
            @RequestParam("cartItemIds") List<Long> cartItemIds,
            @RequestParam(value = "amount", defaultValue = "0") int amount,
            Model model) {

        String orderId = "ORDER-" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // View(결제 페이지)로 데이터 전달
        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", amount);
        model.addAttribute("cartItemIds", cartItemIds); // 폼에 숨겨서 다시 보낼 데이터

        // 단건 강의 정보가 아니라 여러 강의일 수 있으므로 "외 N건" 처리 예시
        String productName = "장바구니 강의 결제";
        model.addAttribute("productName", productName);

        return "payment/checkout";
    }

    // 결제 처리
    @PostMapping("/payment/confirm")
    public String confirmPayment(
            PaymentConfirmRequestDto dto,
            @RequestParam("cartItemIds") List<Long> cartItemIds,
            Authentication authentication
    ) {
        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        paymentService.confirmPayment(dto, authentication);

        User user = userService.getLoginUser(authentication);

        if (cartItemIds != null && !cartItemIds.isEmpty()) {
            enrollmentService.enrollCourses(user, cartItemIds);
        }

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