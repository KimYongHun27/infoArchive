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
public class CustomerInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // 고객센터 문의 고유 번호

    private String category;            // 문의 카테고리

    private String title;               // 문의 제목

    @Column(columnDefinition = "TEXT")
    private String content;             // 문의 내용

    @Column(columnDefinition = "TEXT")
    private String answer;              // 관리자 답변 내용

    @Enumerated(EnumType.STRING)
    private InquiryStatus status;       // 문의 상태: WAITING, ANSWERED

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;                  // 문의 작성자 회원 정보

    private LocalDateTime createdAt;    // 문의 작성일

    private LocalDateTime answeredAt;   // 답변 작성일
}