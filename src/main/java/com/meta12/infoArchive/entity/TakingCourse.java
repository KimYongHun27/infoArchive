package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter

@Table(name = "takingcourse",
        uniqueConstraints = {@UniqueConstraint(name = "uk_user_lecture",
                columnNames = {"user_id", "lecture_id"})})
public class TakingCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //유저 fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //강의 fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Course course;

    //최근 들은 강의 정렬용
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public static TakingCourse createTakingCourse(User user, Course course) {
        TakingCourse takingCourse = new TakingCourse();
        takingCourse.user = user;
        takingCourse.course = course;
        return takingCourse;
    }
}
