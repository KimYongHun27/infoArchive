package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Coupon;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.entity.UserCoupon;
import com.meta12.infoArchive.entity.CouponStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    boolean existsByUserAndCoupon(User user, Coupon coupon);

    // 현재 유저가 보유한 전체 쿠폰 목록 땡겨오기
    List<UserCoupon> findByUser(User user);

    Long countByUserAndStatus(User user, CouponStatus status);
}