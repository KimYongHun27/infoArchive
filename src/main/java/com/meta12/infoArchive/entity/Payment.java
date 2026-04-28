package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //기존 가격
    private int price;

    //할인 가격
    private int discountAmount;

    //(임시) 강의명
    private String lectureName;

    //(임시) 강사명
    private String instructorName;

    // 주문 번호
    private String orderNumber;

    //주문 일시(실제 영수증 및 내역에 저장될 시간)
    private LocalDateTime orderDate;

    //(임시) 강의 카테고리
    private LectureCategory lectureCategory;

    // 결제 상태
    private PaymentStatus paymentStatus;

    //유저 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //강의 정보
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "lecture_id")
//    private Lecture lecture;

    //쿠폰 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
}
