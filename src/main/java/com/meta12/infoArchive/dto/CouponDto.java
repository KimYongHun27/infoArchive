package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponDto {
    private Long Id;

    //coupon code 쿠폰 코드
    private String CouponCode;

    //discountAmount 할인 금액
    private int discountAmount;

    //minOrderAmount 최소 주문 금액
    private int minOrderAmount;
}
