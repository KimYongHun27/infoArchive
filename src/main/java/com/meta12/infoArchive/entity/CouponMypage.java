package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter

@Table(name = "couponmypage",
        uniqueConstraints = {@UniqueConstraint(name = "uk_user_lecture",
                columnNames = {"user_id", "lecture_id"})})
public class CouponMypage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //유저 fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //쿠폰 fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    //D-1 정렬용
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public static CouponMypage createCouponMypage(User user, Coupon coupon) {
        CouponMypage couponMypage = new CouponMypage();
        couponMypage.user = user;
        couponMypage.coupon = coupon;
        return couponMypage;
    }
}
