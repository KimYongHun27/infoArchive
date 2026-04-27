package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerInquiryRequestDto {

    private Long userId;              // 문의 작성자 회원 ID
    private String inquiryCategory;   // 문의 카테고리
    private String inquiryTitle;      // 문의 제목
    private String inquiryContent;    // 문의 내용
}