package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.CouponCountDto;
import com.meta12.infoArchive.dto.CouponDto;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.service.CouponService;
import com.meta12.infoArchive.service.UserCouponService;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class CouponController {

    private final CouponService couponService;
    private final UserCouponService userCouponService;
    private final UserService userService;

    @GetMapping("/coupon")
    public String orders(Authentication authentication, Model model) {
        // 1. 세션에서 로그인한 유저 엔티티 가져오기
        User loginUser = userService.getLoginUser(authentication);

        // 2. 서비스 호출하여 DTO 받아오기 (유저의 ID를 넘겨주네)
        CouponCountDto counts = userCouponService.getCouponCounts(loginUser.getId());

        // 3. 타임리프 화면으로 DTO 통째로 배달하기!
        model.addAttribute("counts", counts);

        return "mypage/coupon";
    }

    @GetMapping("/createCoupon")
    public String createCoupon()
    {
        return "createCoupon";
    }

    @GetMapping("/couponRegistration")
    public String prevRegistrationCoupon()
    {
        return "couponRegistration";
    }

    @PostMapping("/coupons/generate")
    public String generateCoupon(
            CouponDto couponDto
    ) {
        couponService.insert(couponDto);
        return "redirect:/createCoupon";
    }

    @PostMapping("/coupons/couponRegistration")
    public String registrationCoupon(
            @RequestParam("typedCouponCode") String typedCouponCode,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        try {
             User loginUser = userService.getLoginUser(authentication);

            userCouponService.register(loginUser.getId(), typedCouponCode);

            redirectAttributes.addFlashAttribute("message", "쿠폰이 성공적으로 등록되었습니다!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/coupon";
    }
}
