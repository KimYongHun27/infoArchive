package com.meta12.infoArchive.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    //coupon code 쿠폰 코드
    private String CouponCode;
    //discountAmount 할인 금액
    private int discountAmount;
    //resultAmount 적용 금액
    private int resultAmount;
    //minOrderAmount 최소 주문 금액
    private int minOrderAmount;
    //isUsed 사용 여부

    //UsedStatus 사용 상태

    //user 유저
}
