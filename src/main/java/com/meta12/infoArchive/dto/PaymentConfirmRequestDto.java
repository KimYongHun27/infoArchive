package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaymentConfirmRequestDto {

    private String orderId;

    private Integer amount;

    private Integer originalAmount;

    private Integer discountAmount;

    private String membershipType;

    private Long productId;

    private String productName;

    private List<Long> cartIds;

    private Long userCouponId;

    private String paymentMethod;

    private Boolean agreeTerms;

    private String cardNumber;

    private String cardExpire;

    private String cardCvc;

    private String cardPassword;
}