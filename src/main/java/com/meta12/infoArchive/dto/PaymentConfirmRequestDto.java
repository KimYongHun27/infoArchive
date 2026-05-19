package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaymentConfirmRequestDto {

    private String orderId;

    private Integer amount;

    private String membershipType;

    private Long productId;

    private String productName;

    // 장바구니 결제용
    private List<Long> cartIds;

    // 결제 수단
    private String paymentMethod;

    // 약관 동의
    private Boolean agreeTerms;

    // 카드 정보
    private String cardNumber;

    private String cardExpire;

    private String cardCvc;

    private String cardPassword;
}