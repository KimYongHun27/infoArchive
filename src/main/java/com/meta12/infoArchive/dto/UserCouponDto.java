package com.meta12.infoArchive.dto;

import com.meta12.infoArchive.entity.CouponStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCouponDto {
    private Long Id;

    //쿠폰 상태
    private CouponStatus couponStatus;

    //유저 정보
    private int userId;

    //쿠폰 정보
    private int couponId;
}
