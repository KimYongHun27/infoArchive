package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.UserCoupon;
import com.meta12.infoArchive.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    // 내 쿠폰함
    @GetMapping("/coupon")
    public String couponPage(Authentication authentication, Model model) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        List<UserCoupon> myCoupons = couponService.getMyCoupons(authentication);
        Map<String, Long> counts = couponService.getMyCouponCounts(authentication);

        model.addAttribute("myCoupons", myCoupons);
        model.addAttribute("counts", counts);

        return "mypage/coupon";
    }

    // 쿠폰 등록 화면
    @GetMapping("/couponRegistration")
    public String couponRegistrationPage(Authentication authentication) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        return "couponRegistration";
    }

    // 쿠폰 등록 처리
    @PostMapping("/coupons/couponRegistration")
    public String registerCoupon(@RequestParam String typedCouponCode,
                                 Authentication authentication) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        couponService.registerCoupon(authentication, typedCouponCode.trim());

        return "redirect:/coupon";
    }
}