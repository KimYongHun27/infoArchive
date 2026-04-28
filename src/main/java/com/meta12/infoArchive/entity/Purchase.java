package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //유저 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //강의 정보
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "lecture_id")
//    private Lecture lecture;

    //최근 담은 순서 정렬용
    private LocalDateTime createdAt = LocalDateTime.now();

}
