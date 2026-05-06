package com.meta12.infoArchive.dto;

import com.meta12.infoArchive.entity.Order;
import com.meta12.infoArchive.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {

    private Long userId;
    private Long productId;
    private Long userCouponId;
    private int originPrice;
    private int discountPrice;
    private int finalPrice;
}
