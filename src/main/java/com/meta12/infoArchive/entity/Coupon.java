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
    private Long id;

    //쿠폰명
    private String couponName;

    //coupon code 쿠폰 코드
    private String couponCode;

    //discountAmount 할인 금액
    private int discountAmount;

    //minOrderAmount 최소 주문 금액
    private int minOrderAmount;
}
