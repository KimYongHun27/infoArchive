package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WishListDto {

    private Long id;

    // 유저 번호
    private Long userId;

    // 상품 번호 / 강의 상품 번호
    private Long productId;

    // 찜 추가일
    private LocalDateTime createdAt;
}