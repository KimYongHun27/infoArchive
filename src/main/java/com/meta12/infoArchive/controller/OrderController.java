package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.service.OrderService;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/orders")
    public String orders()
    {
        return "mypage/order-details";
    }

    @GetMapping("/membership")
    public String membershipList(Authentication authentication, Model model) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {

            model.addAttribute("login", false);
            model.addAttribute("membershipActive", false);

            return "membership";
        }

        User user = userService.getLoginUser(authentication);
        boolean membershipActive = userService.isMembershipActive(user);

        model.addAttribute("login", true);
        model.addAttribute("user", user);
        model.addAttribute("membershipActive", membershipActive);

        return "membership";
    }

    @PostMapping("/membership/subscribe")
    public String subscribeMembership(Authentication authentication) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        userService.activateMembership(authentication);

        return "redirect:/membership?success=true";
    }
}