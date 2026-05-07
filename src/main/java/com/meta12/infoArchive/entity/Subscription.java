package com.meta12.infoArchive.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("S")
public class Subscription extends Product{

    //구독 기간
    private int durationDays;

    //상품 구매 가능 여부
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus availabilityStatus;
}
