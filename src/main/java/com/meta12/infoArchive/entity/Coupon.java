package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 쿠폰명
    @Column(nullable = false)
    private String couponName;

    // 쿠폰 코드
    @Column(nullable = false, unique = true)
    private String couponCode;

    // 할인 금액
    @Column(nullable = false)
    private int discountAmount;

    // 최소 주문 금액
    @Column(nullable = false)
    private int minOrderAmount;

    // 사용 가능 여부
    @Column(nullable = false)
    private boolean active = true;

    // 생성일
    private LocalDateTime createdAt;

    // 만료일
    private LocalDateTime expiredAt;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }

        if (this.expiredAt == null) {
            this.expiredAt = LocalDateTime.now().plusDays(30);
        }
    }
}