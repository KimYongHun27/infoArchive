package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerInquiryRequestDto {

    private Long userId;
    private String category;
    private String title;
    private String content;
}