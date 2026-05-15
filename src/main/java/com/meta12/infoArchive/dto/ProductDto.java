package com.meta12.infoArchive.dto;

import com.meta12.infoArchive.entity.ProductType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private ProductType productType;

    private String productName;

    private String category;

    private String instructorName;

    private String thumbnailUrl;

    private String videoUrl;

    private String description;

    private int price;

    private Integer discountRate;
}