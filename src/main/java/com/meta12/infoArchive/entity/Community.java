package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String title;

    // 내용
    @Column(columnDefinition = "TEXT")
    private String content;

//    // 작성자
//    @ManyToOne
//    private User author;
}
