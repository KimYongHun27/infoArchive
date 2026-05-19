package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(
        name = "enrollment",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_enrollment_product",
                        columnNames = {"user_id", "product_id"}
                )
        }
)
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 수강한 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 수강 등록된 상품
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 학습 진도율
    private Integer progressRate = 0;

    // 수강 시작일 및 만료일
    private LocalDateTime enrolledAt;

    private LocalDateTime expiredAt;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // 수강 등록 생성 메서드 - 기간 있는 버전
    public static Enrollment createEnrollment(User user, Product product, int periodDays) {
        Enrollment enrollment = new Enrollment();
        enrollment.user = user;
        enrollment.product = product;
        enrollment.progressRate = 0;
        enrollment.enrolledAt = LocalDateTime.now();
        enrollment.expiredAt = LocalDateTime.now().plusDays(periodDays);
        return enrollment;
    }

    // 수강 등록 생성 메서드 - 기본 버전
    public static Enrollment createEnrollment(User user, Product product) {
        Enrollment enrollment = new Enrollment();
        enrollment.user = user;
        enrollment.product = product;
        enrollment.progressRate = 0;
        enrollment.enrolledAt = LocalDateTime.now();
        return enrollment;
    }
}