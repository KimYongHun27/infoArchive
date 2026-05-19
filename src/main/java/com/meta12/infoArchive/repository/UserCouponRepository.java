package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Coupon;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    boolean existsByUserAndCoupon(User user, Coupon coupon);
}
