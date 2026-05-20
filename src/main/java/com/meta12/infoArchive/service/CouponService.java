package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.*;
import com.meta12.infoArchive.repository.CouponRepository;
import com.meta12.infoArchive.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta12.infoArchive.entity.Coupon;
import com.meta12.infoArchive.entity.CouponStatus;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.entity.UserCoupon;

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

    // 결제 페이지에서 사용 가능한 쿠폰 목록
    @Transactional
    public List<UserCoupon> getAvailableCoupons(Authentication authentication) {

        User user = userService.getLoginUser(authentication);

        List<UserCoupon> coupons =
                userCouponRepository.findByUserAndStatusOrderByIssuedAtDesc(user, CouponStatus.AVAILABLE);

        LocalDateTime now = LocalDateTime.now();

        for (UserCoupon userCoupon : coupons) {
            Coupon coupon = userCoupon.getCoupon();

            if (coupon.getExpiredAt() != null && coupon.getExpiredAt().isBefore(now)) {
                userCoupon.setStatus(CouponStatus.EXPIRED);
            }
        }

        return coupons.stream()
                .filter(uc -> uc.getStatus() == CouponStatus.AVAILABLE)
                .toList();
    }


    // 쿠폰 할인금액 계산
    @Transactional(readOnly = true)
    public int calculateDiscount(User user, Long userCouponId, int originalAmount) {

        if (userCouponId == null) {
            return 0;
        }

        UserCoupon userCoupon = userCouponRepository.findById(userCouponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰을 찾을 수 없습니다."));

        if (!userCoupon.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인의 쿠폰만 사용할 수 있습니다.");
        }

        if (userCoupon.getStatus() == CouponStatus.USED) {
            throw new IllegalArgumentException("이미 사용한 쿠폰입니다.");
        }

        if (userCoupon.getStatus() == CouponStatus.EXPIRED) {
            throw new IllegalArgumentException("만료된 쿠폰입니다.");
        }

        Coupon coupon = userCoupon.getCoupon();

        if (!coupon.isActive()) {
            throw new IllegalArgumentException("사용할 수 없는 쿠폰입니다.");
        }

        if (coupon.getExpiredAt() != null && coupon.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("만료된 쿠폰입니다.");
        }

        if (originalAmount < coupon.getMinOrderAmount()) {
            throw new IllegalArgumentException("쿠폰 최소 주문금액을 충족하지 못했습니다.");
        }

        return Math.min(coupon.getDiscountAmount(), originalAmount);
    }


    // 결제 완료 후 쿠폰 사용 처리
    @Transactional
    public void useCoupon(User user, Long userCouponId) {

        if (userCouponId == null) {
            return;
        }

        UserCoupon userCoupon = userCouponRepository.findById(userCouponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰을 찾을 수 없습니다."));

        if (!userCoupon.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인의 쿠폰만 사용할 수 있습니다.");
        }

        if (userCoupon.getStatus() != CouponStatus.AVAILABLE) {
            throw new IllegalArgumentException("사용 가능한 쿠폰이 아닙니다.");
        }

        userCoupon.setStatus(CouponStatus.USED);
        userCoupon.setUsedAt(LocalDateTime.now());
    }

    // 관리자 쿠폰 전체 목록
    @Transactional(readOnly = true)
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }


    // 내 쿠폰 목록
    @Transactional
    public List<UserCoupon> getMyCoupons(Authentication authentication) {

        User user = userService.getLoginUser(authentication);

        List<UserCoupon> myCoupons =
                userCouponRepository.findByUserOrderByIssuedAtDesc(user);

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


    // 내 쿠폰 개수
    @Transactional(readOnly = true)
    public Map<String, Long> getMyCouponCounts(Authentication authentication) {

        User user = userService.getLoginUser(authentication);

        Map<String, Long> counts = new HashMap<>();

        counts.put("unused",
                userCouponRepository.countByUserAndStatus(user, CouponStatus.AVAILABLE));

        counts.put("used",
                userCouponRepository.countByUserAndStatus(user, CouponStatus.USED));

        counts.put("expired",
                userCouponRepository.countByUserAndStatus(user, CouponStatus.EXPIRED));

        return counts;
    }
}