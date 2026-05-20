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

    @GetMapping("/admin/coupons")
    public String adminCoupons(Model model) {

        List<Coupon> coupons = couponService.getAllCoupons();

        model.addAttribute("coupons", coupons);
        model.addAttribute("couponCount", coupons.size());

        return "createCoupon";
    }

    @PostMapping("/coupons/generate")
    public String generateCoupon(@RequestParam String couponName,
                                 @RequestParam(defaultValue = "10000") int discountAmount,
                                 @RequestParam(defaultValue = "50000") int minOrderAmount,
                                 @RequestParam(defaultValue = "30") int validDays) {

        couponService.createCoupon(
                couponName,
                discountAmount,
                minOrderAmount,
                validDays
        );

        return "redirect:/admin/coupons";
    }
}