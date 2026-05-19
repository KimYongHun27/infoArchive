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
        name = "taking_course",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_product",
                        columnNames = {"user_id", "product_id"}
                )
        }
)
public class TakingCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 수강 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 기존 lecture_id 제거하고 product_id로 변경
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 학습 진도율
    private Integer progressRate = 0;

    // 수강 시작일
    private LocalDateTime startedAt;

    // 마지막 학습일
    private LocalDateTime lastStudiedAt;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public static TakingCourse createTakingCourse(User user, Product product) {
        TakingCourse takingCourse = new TakingCourse();
        takingCourse.user = user;
        takingCourse.product = product;
        takingCourse.progressRate = 0;
        takingCourse.startedAt = LocalDateTime.now();
        return takingCourse;
    }
}