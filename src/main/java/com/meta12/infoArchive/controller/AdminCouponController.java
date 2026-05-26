package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.Coupon;
import com.meta12.infoArchive.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminCouponController {

    private final CouponService couponService;

    // 관리자 쿠폰 목록
    @GetMapping("/admin/coupons")
    public String adminCoupons(Model model) {

        List<Coupon> coupons = couponService.getAllCoupons();

        model.addAttribute("coupons", coupons);
        model.addAttribute("couponCount", coupons.size());

        return "createCoupon";
    }

    // 쿠폰 생성
    @PostMapping("/coupons/generate")
    public String generateCoupon(
            @RequestParam String couponName,
            @RequestParam(defaultValue = "10000") int discountAmount,
            @RequestParam(defaultValue = "50000") int minOrderAmount,
            @RequestParam(defaultValue = "30") int validDays
    ) {
        couponService.createCoupon(
                couponName,
                discountAmount,
                minOrderAmount,
                validDays
        );

        return "redirect:/admin/coupons?createSuccess=true";
    }

    // 쿠폰 중지
    @PostMapping("/admin/coupons/{couponId}/stop")
    public String stopCoupon(
            @PathVariable Long couponId
    ) {
        couponService.stopCoupon(couponId);

        return "redirect:/admin/coupons?stopSuccess=true";
    }

    // 쿠폰 재개
    @PostMapping("/admin/coupons/{couponId}/resume")
    public String resumeCoupon(
            @PathVariable Long couponId
    ) {
        couponService.resumeCoupon(couponId);

        return "redirect:/admin/coupons?resumeSuccess=true";
    }

    // 쿠폰 삭제
    @PostMapping("/admin/coupons/{couponId}/delete")
    public String deleteCoupon(
            @PathVariable Long couponId
    ) {
        couponService.deleteCoupon(couponId);

        return "redirect:/admin/coupons?deleteSuccess=true";
    }
}