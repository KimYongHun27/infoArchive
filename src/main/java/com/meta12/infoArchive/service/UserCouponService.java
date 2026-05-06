package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.UserCoupon;
import com.meta12.infoArchive.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserCouponService {
    private final UserCouponRepository userCouponRepository;

    public UserCoupon getUserCoupon(Long id)
    {
        Optional<UserCoupon> optionalUserCoupon = userCouponRepository.findById(id);
        UserCoupon userCoupon= null;

        if (optionalUserCoupon.isPresent())
        {
            userCoupon = optionalUserCoupon.get();
        }

        return userCoupon;
    }
}
