package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //주문 가격
    private int orderPrice;
    //주문 번호
    private int orderNumber;

    //주문 회원 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")

    //주문 회원 정보
    private User user;

    //주문 날짜
    private LocalDateTime createdAt;

    //상품 fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    //유저 별 쿠폰 fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userCoupon_id")
    private UserCoupon userCoupon;

}
