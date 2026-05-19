package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.CouponDto;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.service.CouponService;
import com.meta12.infoArchive.service.UserCouponService;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
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
    public String orders()
    {
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
            Authentication authentication, // ⭕ @AuthenticationPrincipal 대신 Authentication 객체를 직접 받음!
            RedirectAttributes redirectAttributes
    ) {
        try {
            // 1. 자네가 UserService에 만들어둔 명품 메서드로 진짜 User 엔티티를 찾아오네!
            // (대신 컨트롤러에 UserService 주입이 필요하네. 코드 상단에 private final UserService userService; 추가 필수!)
            User loginUser = userService.getLoginUser(authentication);

            // 2. 찾아온 진짜 유저의 ID와 코드를 서비스로 전달!
            userCouponService.register(loginUser.getId(), typedCouponCode);

            redirectAttributes.addFlashAttribute("message", "쿠폰이 성공적으로 등록되었습니다!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/coupon";
    }
}
