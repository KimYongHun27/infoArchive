package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //상품 종류(구독, 강의)
    private ProductType productType;

    //가격
    private int price;

    //구독권 사용 상태
    private SubscriptionStatus subscriptionStatus;

    //구독 시작 일시(연, 월, 일, 시, 분)
    private LocalDateTime start_time;

    //구독 종료 일시(연, 월, 일, 시, 분)
    private LocalDateTime end_time;

}
