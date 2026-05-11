package com.meta12.infoArchive.dto;

import com.meta12.infoArchive.entity.Order;
import com.meta12.infoArchive.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {

    //유저 fk
    private Long userId;
    //상품 fk
    private Long productId;
    //쿠폰 fk
    private Long userCouponId;
}
