package com.meta12.infoArchive.dto;

import com.meta12.infoArchive.entity.LectureCategory;
import com.meta12.infoArchive.entity.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentDto {
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
    private int userId;

    //강의 정보
    private int lectureId;

    //쿠폰 정보
    private int couponId;
}
