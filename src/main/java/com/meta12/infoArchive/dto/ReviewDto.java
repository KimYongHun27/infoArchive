package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDto {

    private Long id;

    // 상품/강의 ID
    private Long productId;

    // 평점
    private Integer rating;

    // 리뷰 제목
    private String title;

    // 리뷰 내용
    private String content;

    // 화면 출력용 작성자 이름
    private String nickname;

    // 작성일
    private LocalDateTime createDate;
}