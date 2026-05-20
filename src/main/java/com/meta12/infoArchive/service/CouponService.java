package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.*;
import com.meta12.infoArchive.repository.CouponRepository;
import com.meta12.infoArchive.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final UserService userService;

    // 관리자 쿠폰 자동 생성
    @Transactional
    public void createCoupon(String couponName,
                             int discountAmount,
                             int minOrderAmount,
                             int validDays) {

        String couponCode = generateCouponCode();

        while (couponRepository.existsByCouponCode(couponCode)) {
            couponCode = generateCouponCode();
        }

        Coupon coupon = new Coupon();
        coupon.setCouponName(couponName);
        coupon.setCouponCode(couponCode);
        coupon.setDiscountAmount(discountAmount);
        coupon.setMinOrderAmount(minOrderAmount);
        coupon.setActive(true);
        coupon.setExpiredAt(LocalDateTime.now().plusDays(validDays));

        couponRepository.save(coupon);
    }

    private String generateCouponCode() {
        String randomCode = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8)
                .toUpperCase();

        return "INFO-" + randomCode;
    }

    // 유저가 쿠폰 코드 등록
    @Transactional
    public void registerCoupon(Authentication authentication, String typedCouponCode) {

        User user = userService.getLoginUser(authentication);

        Coupon coupon = couponRepository.findByCouponCode(typedCouponCode)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰 코드입니다."));

        if (!coupon.isActive()) {
            throw new IllegalArgumentException("사용할 수 없는 쿠폰입니다.");
        }

        if (coupon.getExpiredAt() != null && coupon.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("만료된 쿠폰입니다.");
        }

        UserCoupon existingUserCoupon =
                userCouponRepository.findByUserAndCoupon(user, coupon).orElse(null);

        if (existingUserCoupon != null) {

            if (existingUserCoupon.getStatus() == CouponStatus.USED) {
                throw new IllegalArgumentException("이미 사용한 쿠폰입니다.");
            }

            if (existingUserCoupon.getStatus() == CouponStatus.EXPIRED) {
                throw new IllegalArgumentException("이미 만료된 쿠폰입니다.");
            }

            if (existingUserCoupon.getStatus() == CouponStatus.AVAILABLE) {
                throw new IllegalArgumentException("이미 등록한 쿠폰입니다.");
            }
        }

        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUser(user);
        userCoupon.setCoupon(coupon);
        userCoupon.setStatus(CouponStatus.AVAILABLE);

        userCouponRepository.save(userCoupon);
    }

    @Transactional
    public List<UserCoupon> getMyCoupons(Authentication authentication) {

        User user = userService.getLoginUser(authentication);

        List<UserCoupon> myCoupons = userCouponRepository.findByUserOrderByIssuedAtDesc(user);

        LocalDateTime now = LocalDateTime.now();

        for (UserCoupon userCoupon : myCoupons) {
            Coupon coupon = userCoupon.getCoupon();

            if (userCoupon.getStatus() == CouponStatus.AVAILABLE
                    && coupon.getExpiredAt() != null
                    && coupon.getExpiredAt().isBefore(now)) {
                userCoupon.setStatus(CouponStatus.EXPIRED);
            }
        }

        return myCoupons;
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getMyCouponCounts(Authentication authentication) {

        User user = userService.getLoginUser(authentication);

        Map<String, Long> counts = new HashMap<>();
        counts.put("unused", userCouponRepository.countByUserAndStatus(user, CouponStatus.AVAILABLE));
        counts.put("used", userCouponRepository.countByUserAndStatus(user, CouponStatus.USED));
        counts.put("expired", userCouponRepository.countByUserAndStatus(user, CouponStatus.EXPIRED));

        return counts;
    }

    @Transactional(readOnly = true)
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }
}