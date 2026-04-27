package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorApplyRequestDto {

    private Long userId;
    private String title;
    private String intro;
    private String career;
    private String portfolioUrl;
}