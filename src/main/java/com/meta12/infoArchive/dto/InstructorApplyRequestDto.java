package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorApplyRequestDto {

    private Long userId;                    // 신청자 회원 ID
    private String applicationTitle;        // 강사 신청 제목
    private String instructorIntro;         // 강사 소개
    private String instructorCareer;        // 강사 경력
    private String portfolioUrl;            // 포트폴리오 주소
}