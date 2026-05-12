package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //할인이 적용된 최종가격
    @Column(nullable = false)
    private int discountPrice;

    // 주문 번호
    @Column(unique = true, nullable = false)
    private String orderNumber;

    //주문 일시(실제 영수증 및 내역에 저장될 시간)
    @Column(nullable = false, updatable = false)
    private LocalDateTime orderDate;

    // 결제 상태
    @Enumerated(EnumType.STRING) // 숫자가 밀려 발생할 오류 방지
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    //주문 정보 fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
