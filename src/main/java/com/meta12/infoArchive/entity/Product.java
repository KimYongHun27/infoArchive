package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 상품 종류
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType productType;

    // 상품명 / 강의명
    @Column(nullable = false)
    private String productName;

    // 카테고리
    private String category;

    // 강사명
    private String instructorName;

    // 썸네일
    private String thumbnailUrl;

    // 강의 설명
    @Column(length = 2000)
    private String description;

    // 가격
    private int price;

    // 할인율
    private Integer discountRate;

    // 승인 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    // 등록일
    private LocalDateTime createdAt;

    // 승인/반려 처리일
    private LocalDateTime reviewedAt;

    // 반려 사유
    @Column(length = 1000)
    private String rejectReason;

    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = ProductStatus.WAITING;
        }

        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}