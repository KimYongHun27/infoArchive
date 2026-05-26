package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.Coupon;
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

    /*
     * =========================
     * 관리자 쿠폰 관리
     * =========================
     */

    @GetMapping("/admin/coupons")
    public String adminCoupons(Model model) {

        List<Coupon> coupons = couponService.getAllCoupons();

        model.addAttribute("coupons", coupons);
        model.addAttribute("couponCount", coupons.size());

        return "admin-coupons";
    }

    @PostMapping("/coupons/generate")
    public String generateCoupon(
            @RequestParam String couponName,
            @RequestParam int discountAmount,
            @RequestParam int minOrderAmount,
            @RequestParam int validDays,
            RedirectAttributes redirectAttributes
    ) {
        try {
            couponService.createCoupon(
                    couponName,
                    discountAmount,
                    minOrderAmount,
                    validDays
            );

            redirectAttributes.addFlashAttribute("successMessage", "쿠폰이 생성되었습니다.");
            return "redirect:/admin/coupons?createSuccess=true";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/coupons?createError=true";
        }
    }

    @PostMapping("/admin/coupons/{couponId}/stop")
    public String stopCoupon(
            @PathVariable Long couponId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            couponService.stopCoupon(couponId);
            redirectAttributes.addFlashAttribute("successMessage", "쿠폰이 중지되었습니다.");
            return "redirect:/admin/coupons?stopSuccess=true";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/coupons?stopError=true";
        }
    }

    @PostMapping("/admin/coupons/{couponId}/resume")
    public String resumeCoupon(
            @PathVariable Long couponId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            couponService.resumeCoupon(couponId);
            redirectAttributes.addFlashAttribute("successMessage", "쿠폰이 다시 사용 가능 상태로 변경되었습니다.");
            return "redirect:/admin/coupons?resumeSuccess=true";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/coupons?resumeError=true";
        }
    }

    @PostMapping("/admin/coupons/{couponId}/delete")
    public String deleteCoupon(
            @PathVariable Long couponId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            couponService.deleteCoupon(couponId);
            redirectAttributes.addFlashAttribute("successMessage", "쿠폰이 삭제되었습니다.");
            return "redirect:/admin/coupons?deleteSuccess=true";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/coupons?deleteError=true";
        }
    }

    /*
     * =========================
     * 일반회원 쿠폰함
     * =========================
     */

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