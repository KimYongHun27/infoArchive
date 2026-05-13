package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("S")
public class Subscription extends Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //구독 기간
    private int durationDays;

    //상품 구매 가능 여부
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus availabilityStatus;
}
