package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Coupon;
import com.meta12.infoArchive.entity.CouponStatus;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    List<UserCoupon> findByUser(User user);

    List<UserCoupon> findByUserOrderByIssuedAtDesc(User user);

    boolean existsByUserAndCoupon(User user, Coupon coupon);

    long countByUserAndStatus(User user, CouponStatus status);
}