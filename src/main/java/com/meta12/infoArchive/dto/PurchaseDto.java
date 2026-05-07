package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PurchaseDto {
    private Long id;

    //주문 정보 fk
    private Long orderId;

    //상품 가격
    private int price;

    //상품명
    private String productName;

    //구매일
    private LocalDateTime createAt;
}
