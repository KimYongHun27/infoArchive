package com.meta12.infoArchive.dto;

import com.meta12.infoArchive.entity.ApplyStatus;
import com.meta12.infoArchive.entity.InstructorApply;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InstructorApplyResponseDto {

    private Long id;                    // 강사 신청 고유 번호
    private Long userId;                // 신청자 회원 ID
    private String username;            // 신청자 아이디
    private String name;                // 신청자 이름

    private String title;               // 강사 신청 제목
    private String intro;               // 강사 소개
    private String career;              // 강사 경력
    private String portfolioUrl;        // 포트폴리오 주소

    private ApplyStatus status;         // 신청 상태
    private String rejectReason;        // 반려 사유

    private LocalDateTime createdAt;    // 신청일
    private LocalDateTime reviewedAt;   // 검토일

    public InstructorApplyResponseDto(InstructorApply instructorApply) {
        this.id = instructorApply.getId();

        if (instructorApply.getUser() != null) {
            this.userId = instructorApply.getUser().getId();
            this.username = instructorApply.getUser().getUsername();
            this.name = instructorApply.getUser().getName();
        }

        this.title = instructorApply.getTitle();
        this.intro = instructorApply.getIntro();
        this.career = instructorApply.getCareer();
        this.portfolioUrl = instructorApply.getPortfolioUrl();
        this.status = instructorApply.getStatus();
        this.rejectReason = instructorApply.getRejectReason();
        this.createdAt = instructorApply.getCreatedAt();
        this.reviewedAt = instructorApply.getReviewedAt();
    }
}