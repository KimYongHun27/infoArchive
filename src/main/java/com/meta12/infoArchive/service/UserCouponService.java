package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.CouponCountDto;
import com.meta12.infoArchive.entity.*;
import com.meta12.infoArchive.repository.CouponRepository;
import com.meta12.infoArchive.repository.UserCouponRepository;
import com.meta12.infoArchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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


    public CouponCountDto getCouponCounts(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));


        LocalDateTime now = LocalDateTime.now();

        // 1. 보유 쿠폰 개수 (사용 가능 상태)
        Long unused = userCouponRepository.countByUserAndStatus(user, CouponStatus.AVAILABLE);

// 2. 사용 완료 쿠폰 개수 (사용된 상태)
        Long used = userCouponRepository.countByUserAndStatus(user, CouponStatus.USED);

// 3. 기간 만료 쿠폰 개수 (기한 만료 상태)
        Long expired = userCouponRepository.countByUserAndStatus(user, CouponStatus.EXPIRATION);
        return new CouponCountDto(unused, used, expired);
    }

    public List<UserCoupon> getSelectUser(Long userId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return userCouponRepository.findByUser(user);
    }
}
