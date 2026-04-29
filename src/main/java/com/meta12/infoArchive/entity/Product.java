package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //상품 종류(구독)
    private ProductType productType;

    //강사 fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    //강의 fk
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "lecture_id")
//    private Lecture lecture;
}
