package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "special_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecialLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 실행한 기능명
    @Column(nullable = false)
    private String actionName;

    // 성공, 실패, 대기
    @Column(nullable = false)
    private String status;

    // 실행 메시지
    @Column(length = 1000)
    private String message;

    // 실행한 계정 이메일
    private String executedBy;

    // 실행 시간
    private LocalDateTime executedAt;

    @PrePersist
    public void prePersist() {
        if (this.executedAt == null) {
            this.executedAt = LocalDateTime.now();
        }
    }
}