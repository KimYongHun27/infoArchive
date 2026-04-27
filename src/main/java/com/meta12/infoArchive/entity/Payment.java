package com.meta12.infoArchive.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

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

}
