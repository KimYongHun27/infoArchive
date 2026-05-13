package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class UserSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //상품 fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private User product;

    //유저 fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //구독 fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id")
    private User subscription;

    //구독 시작일
    private LocalDateTime startDate;
    //구독 종료일
    private LocalDateTime endDate;
    //최종 업데이트 일자
    private LocalDateTime updated_at;
    //주문 번호
    private Long orderId;
    //갱신 여부
    private boolean isAutoRenew;
}
