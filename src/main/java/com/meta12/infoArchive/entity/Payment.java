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

    //할인이 적용된 최종가격
    private int discountPrice;

    // 주문 번호
    private String orderNumber;

    //주문 일시(실제 영수증 및 내역에 저장될 시간)
    private LocalDateTime orderDate;

    // 결제 상태
    private PaymentStatus paymentStatus;

    //주문 정보 fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
