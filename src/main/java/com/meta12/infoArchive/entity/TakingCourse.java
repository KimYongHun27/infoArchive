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
public class TakingCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                 // 강의 고유 번호

    private String title;             // 강의 이름
    private String instructor;       // 강사 이름
    private String category;         // 강의 분야

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;                 // 연결된 회원 정보

    private Integer progressRate = 0; // 학습 진도율 (0 ~ 100%)

    private LocalDateTime createdAt;  // 강의 등록일

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}