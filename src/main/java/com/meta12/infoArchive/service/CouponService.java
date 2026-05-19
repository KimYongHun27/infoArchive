package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.CouponDto;
import com.meta12.infoArchive.entity.Coupon;
import com.meta12.infoArchive.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public void insert(CouponDto couponDto)
    {
        Coupon coupon = new Coupon();
        if (couponDto != null) {
            coupon.setCouponCode(couponDto.getCouponCode());
            coupon.setDiscountAmount(couponDto.getDiscountAmount());
            coupon.setMinOrderAmount(couponDto.getMinOrderAmount());
        }
        coupon.setCouponCode("1234");
        couponRepository.save(coupon);
    }


    public Coupon getCoupon(Long id) {
        Optional<Coupon> optional = couponRepository.findById(id);
        return optional.orElse(null);
    }
}
