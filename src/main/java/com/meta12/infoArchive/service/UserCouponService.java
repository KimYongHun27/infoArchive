package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.Coupon;
import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.entity.UserCoupon;
import com.meta12.infoArchive.repository.CouponRepository;
import com.meta12.infoArchive.repository.UserCouponRepository;
import com.meta12.infoArchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserCouponService {
    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    @Transactional
    public void register(Long userId, String typedCouponCode) {
        // 1. 세션에서 넘어온 유저가 실제 DB에 있는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 2. [핵심] 입력한 쿠폰 코드가 진짜 존재하는 쿠폰인지 확인!
        // (이를 위해 CouponRepository에 findByCouponCode 메서드가 필요하네)
        Coupon coupon = couponRepository.findByCouponCode(typedCouponCode)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 쿠폰 번호입니다."));

        // 3. [보안] 이 유저가 이미 이 쿠폰을 등록한 적이 없는지 중복 체크!
        boolean alreadyHasIt = userCouponRepository.existsByUserAndCoupon(user, coupon);
        if (alreadyHasIt) {
            throw new IllegalArgumentException("이미 등록하신 쿠폰입니다.");
        }

        // 4. 검증 완료되면 매핑 테이블(UserCoupon)에 슥 저장!
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUser(user);
        userCoupon.setCoupon(coupon);

        userCouponRepository.save(userCoupon);
    }
}
