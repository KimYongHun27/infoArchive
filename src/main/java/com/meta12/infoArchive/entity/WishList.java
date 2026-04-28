package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //최근 담은 순서 정렬용
    private LocalDateTime createdAt;

    @ManyToOne
    private User user;

    //강의 정보
//    @ManyToOne
//    private Lecture lecture;
}
