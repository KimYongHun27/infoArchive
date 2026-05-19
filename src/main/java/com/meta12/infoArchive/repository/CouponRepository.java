package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Coupon;
import com.meta12.infoArchive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findByCouponCode(String couponCode);
}
