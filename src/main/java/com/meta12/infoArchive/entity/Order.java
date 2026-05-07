package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //가격 정보 스냅샷
    private int orderPrice;

    //유저 fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //상품 fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    //유저 별 쿠폰 fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userCoupon_id")
    private UserCoupon userCoupon;
}
