package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.PipedReader;

@Getter
@Setter
public class PaymentConfirmRequestDto {

    private String orderId;
    private Long productId;
    private Integer amount;
    private String paymentMethod;
    private String membershipType;
    private Boolean agreeTerms;

    // MOCK 카드 정보
    private String cardNumber;
    private String cardExpire;
    private String cardCvc;
    private String cardPassword;

}