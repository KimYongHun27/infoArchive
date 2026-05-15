package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductDto {

    private String productName;

    private String category;

    private String instructorName;

    // 썸네일 파일 업로드
    private MultipartFile thumbnailFile;

    // 영상은 URL로 받기
    private String videoUrl;

    private String description;

    private int price;

    private Integer discountRate;
}