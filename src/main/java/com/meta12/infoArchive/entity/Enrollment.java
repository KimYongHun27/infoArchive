package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "cart", uniqueConstraints = {@UniqueConstraint(name = "uk_user_cart_lecture", columnNames = {"user_id", "lecture_id"})})
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Course course;

    // 학습 진도율 (0 ~ 100%)
    private Integer progressRate = 0;

    // 수강 시작일 및 만료일
    private LocalDateTime enrolledAt;
    private LocalDateTime expiredAt;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // 수강 등록 생성 메서드
    public static Enrollment createEnrollment(User user, Course course, int periodDays) {
        Enrollment enrollment = new Enrollment();
        enrollment.user = user;
        enrollment.course = course;
        enrollment.enrolledAt = LocalDateTime.now();
        enrollment.expiredAt = LocalDateTime.now().plusDays(periodDays);
        return enrollment;
    }
}
