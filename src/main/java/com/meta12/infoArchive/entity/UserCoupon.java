package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(
        name = "user_coupon",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_coupon",
                        columnNames = {"user_id", "coupon_id"}
                )
        }
)
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 쿠폰을 보유한 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 쿠폰 원본
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    // 쿠폰 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponStatus status = CouponStatus.AVAILABLE;

    // 발급일
    private LocalDateTime issuedAt;

    // 사용일
    private LocalDateTime usedAt;

    @PrePersist
    public void prePersist() {
        if (this.issuedAt == null) {
            this.issuedAt = LocalDateTime.now();
        }

        if (this.status == null) {
            this.status = CouponStatus.AVAILABLE;
        }
    }
}
