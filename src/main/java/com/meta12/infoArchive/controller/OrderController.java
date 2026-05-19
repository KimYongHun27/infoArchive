package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.Payment;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.PaymentRepository;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final UserService userService;
    private final PaymentRepository paymentRepository;

    @GetMapping("/orders")
    public String orders(Authentication authentication, Model model) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        User user = userService.getLoginUser(authentication);

        List<Payment> payments =
                paymentRepository.findByUserOrderByOrderDateDesc(user);

        long totalCount = paymentRepository.countByUser(user);
        long courseCount = paymentRepository.countByUserAndProductIsNotNull(user);
        long membershipCount = paymentRepository.countByUserAndProductIsNull(user);

        model.addAttribute("payments", payments);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("courseCount", courseCount);
        model.addAttribute("membershipCount", membershipCount);

        return "mypage/order-details";
    }
}