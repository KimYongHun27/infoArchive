package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstructorApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                   // 강사 신청 고유 번호

    private String title;              // 강사 신청 제목
    private String intro;              // 강사 소개
    private String career;             // 강사 경력
    private String portfolioUrl;       // 포트폴리오 주소

    @Enumerated(EnumType.STRING)
    private ApplyStatus status;        // 신청 상태

    private String rejectReason;       // 반려 사유

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;                 // 신청한 회원 정보

    private LocalDateTime createdAt;   // 신청일
    private LocalDateTime reviewedAt;  // 검토일
}