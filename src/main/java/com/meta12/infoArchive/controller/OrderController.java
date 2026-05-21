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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final UserService userService;
    private final PaymentRepository paymentRepository;

    @GetMapping("/orders")
    public String orders(
            Authentication authentication,
            @RequestParam(value = "type", defaultValue = "ALL") String type,
            Model model
    ) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        User user = userService.getLoginUser(authentication);

        if (!type.equals("ALL") && !type.equals("COURSE") && !type.equals("ETC")) {
            type = "ALL";
        }

        List<Payment> payments;

        if (type.equals("COURSE")) {
            payments = paymentRepository.findByUserAndProductIsNotNullOrderByOrderDateDesc(user);
        } else if (type.equals("ETC")) {
            payments = paymentRepository.findByUserAndProductIsNullOrderByOrderDateDesc(user);
        } else {
            payments = paymentRepository.findByUserOrderByOrderDateDesc(user);
        }

        long allCount = paymentRepository.countByUser(user);
        long courseCount = paymentRepository.countByUserAndProductIsNotNull(user);
        long etcCount = paymentRepository.countByUserAndProductIsNull(user);

        model.addAttribute("type", type);
        model.addAttribute("payments", payments);

        model.addAttribute("allCount", allCount);
        model.addAttribute("courseCount", courseCount);
        model.addAttribute("etcCount", etcCount);

        return "mypage/order-details";
    }

    @GetMapping("/orders/{id}")
    public String orderDetail(
            @PathVariable("id") Long id,
            Authentication authentication,
            Model model
    ) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        User user = userService.getLoginUser(authentication);

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("주문 내역을 찾을 수 없습니다."));

        if (payment.getUser() == null || !payment.getUser().getId().equals(user.getId())) {
            return "redirect:/orders";
        }

        model.addAttribute("payment", payment);

        return "mypage/order-detail";
    }
}