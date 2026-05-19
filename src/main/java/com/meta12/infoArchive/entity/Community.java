package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    @Column(length = 200)
    private String title;

    private String category; // 카테고리

    // 내용
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    // Community 엔티티 클래스 내부에 추가
    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)
    @OrderBy("id DESC")
    private List<Answer> answers;
}
