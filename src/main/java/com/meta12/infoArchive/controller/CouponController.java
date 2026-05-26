package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.CouponStatus;
import com.meta12.infoArchive.entity.UserCoupon;
import com.meta12.infoArchive.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/coupon")
    public String couponPage(
            Authentication authentication,
            @RequestParam(value = "status", defaultValue = "AVAILABLE") String status,
            Model model
    ) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        if (!status.equals("AVAILABLE")
                && !status.equals("USED")
                && !status.equals("EXPIRED")) {
            status = "AVAILABLE";
        }

        CouponStatus couponStatus = CouponStatus.valueOf(status);

        List<UserCoupon> myCoupons = couponService.getMyCoupons(authentication)
                .stream()
                .filter(userCoupon -> userCoupon.getStatus() == couponStatus)
                .collect(Collectors.toList());

        Map<String, Long> counts = couponService.getMyCouponCounts(authentication);

        model.addAttribute("myCoupons", myCoupons);
        model.addAttribute("counts", counts);
        model.addAttribute("status", status);

        return "mypage/coupon";
    }

    // 기존 주소도 살려둠
    @GetMapping("/couponRegistration")
    public String couponRegistrationPage(Authentication authentication) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        return "couponregistration";
    }

    // 쿠폰함의 "쿠폰 등록 +" 버튼용 주소
    @GetMapping("/coupon/register")
    public String couponRegister(Authentication authentication) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        return "couponregistration";
    }

    @PostMapping("/coupons/couponRegistration")
    public String registerCoupon(
            @RequestParam String typedCouponCode,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        try {
            couponService.registerCoupon(authentication, typedCouponCode.trim());
            redirectAttributes.addFlashAttribute("successMessage", "쿠폰이 등록되었습니다.");
            return "redirect:/coupon";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/coupon/register";
        }
    }
}