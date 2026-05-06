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
    private int originPrice;
    private int discountPrice;
    private int finalPrice;

    public OrderRequestDto(Order order, Product product)
    {
        this.userId = order.getId();
        this.productId = product.getId();
        this.originPrice = product.getPrice();
        this.discountPrice = 0;
        this.finalPrice = originPrice - discountPrice;
    }

}
