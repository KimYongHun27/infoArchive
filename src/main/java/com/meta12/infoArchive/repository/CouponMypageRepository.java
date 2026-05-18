package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.CouponMypage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponMypageRepository extends JpaRepository<CouponMypage, Long> {
}
