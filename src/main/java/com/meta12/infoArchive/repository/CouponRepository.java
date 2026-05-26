package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findByCouponCode(String couponCode);

    boolean existsByCouponCode(String couponCode);

    // 관리자 쿠폰 목록: 삭제되지 않은 쿠폰만 조회
    List<Coupon> findByDeletedFalseOrderByIdDesc();
}