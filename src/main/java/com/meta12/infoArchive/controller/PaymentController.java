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
import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.service.ProductService;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;
    private final ProductService productService;

    // 결제 페이지
    @GetMapping("/payment/checkout")
    public String checkoutPage(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) String membershipType,
            Authentication authentication,
            Model model
    ) {
        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        User user = userService.getLoginUser(authentication);

        // 멤버십 이용 중인 사람이 강의 결제 페이지로 접근하면 다시 강의 상세로 보냄
        if (productId != null && userService.isMembershipActive(user)) {
            return "redirect:/product/" + productId;
        }

        String orderId = "ORDER-" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // 강의 결제
        if (productId != null) {
            Product product = productService.getProduct(productId);

            if (product == null) {
                return "redirect:/top10";
            }

            model.addAttribute("orderId", orderId);
            model.addAttribute("amount", product.getPrice());
            model.addAttribute("product", product);
            model.addAttribute("productId", product.getId());
            model.addAttribute("productName", product.getProductName());
            model.addAttribute("membershipType", "COURSE");

            return "payment/checkout";
        }

        // 멤버십 결제
        if ("MONTHLY".equals(membershipType)) {

            if (userService.isMembershipActive(user)) {
                return "redirect:/membership?already=true";
            }

            model.addAttribute("orderId", orderId);
            model.addAttribute("amount", 239000);
            model.addAttribute("productId", null);
            model.addAttribute("productName", "INFO ARCHIVE 프리미엄 멤버십");
            model.addAttribute("membershipType", "MONTHLY");

            return "payment/checkout";
        }

        return "redirect:/top10";
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

        try {
            paymentService.confirmPayment(dto, authentication);

            if ("MONTHLY".equals(dto.getMembershipType())) {
                userService.activateMembership(authentication);
            }

            return "redirect:/payment/payment-complete";

        } catch (IllegalArgumentException e) {
            return "redirect:/membership?already=true";
        }
    }

    @GetMapping("/payment/payment-complete")
    public String paymentCompletePage() {
        return "payment/payment-complete";
    }
}