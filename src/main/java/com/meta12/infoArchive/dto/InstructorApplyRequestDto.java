package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorApplyRequestDto {

    private Long userId;          // 신청자 회원 ID
    private String title;         // 강사 신청 제목
    private String intro;         // 강사 소개
    private String career;        // 강사 경력
    private String portfolioUrl;  // 포트폴리오 주소
}