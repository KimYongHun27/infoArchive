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
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                 // 강사 고유 번호

    private String nickname;         // 강사 닉네임
    private String intro;            // 강사 소개
    private String career;           // 강사 경력
    private String category;         // 강의 분야

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;               // 연결된 회원 정보

    private LocalDateTime createdAt; // 강사 등록일
}