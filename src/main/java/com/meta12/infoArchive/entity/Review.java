package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 내용
    @Column(columnDefinition = "TEXT")
    private String content;

    // 제목
    @Column(length = 200)
    private  String title;

    // 유저 작성자 연동
    @ManyToOne
    private User name;

    // 강사 작성자 연동
    @ManyToOne
    private Instructor nickname;

    // 작성일
    private LocalDateTime createDate;
}
