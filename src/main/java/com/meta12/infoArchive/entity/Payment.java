package com.meta12.infoArchive.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //가격
    private int price;

    //(임시) 강의명
    private String lectureName;

    //(임시) 강사명
    private String instructorName;

    //(임시) 주문 번호
    private String OrderNumber;

    //(임시) 주문 일시
    private CreatedDate OrderDate;

    //(임시) 강의 카테고리
    private LectureCategory lectureCategory;

    //(임시) 결제 상태
    private PaymentStatus paymentStatus;

    //유저

    //강의
}
