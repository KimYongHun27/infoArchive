package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 할인이 적용된 최종 결제금액
    @Column(name = "discount_amount", nullable = false)
    private int discountAmount;

    // 주문 번호
    @Column(unique = true, nullable = false)
    private String orderNumber;

    // 주문 일시
    @Column(nullable = false, updatable = false)
    private LocalDateTime orderDate;

    // 결제 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    // 주문 정보 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    // 결제 회원 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}