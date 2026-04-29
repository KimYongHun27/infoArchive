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

    // 유저 작성자 연동
    @ManyToOne
    private User author;

    // 강사 작성자 연동
    @ManyToOne
    private Instructor nickname;

    private LocalDateTime createDate; // 작성일
}
