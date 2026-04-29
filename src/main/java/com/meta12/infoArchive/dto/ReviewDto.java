package com.meta12.infoArchive.dto;

import com.meta12.infoArchive.entity.Instructor;
import com.meta12.infoArchive.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDto {

    private Long id;

    private  String title;

    // 내용
    private String content;

    // 작성자
    private User nameid;
    //id
    // 강사 작성자 연동
    private Instructor nicknameid;

    // 날짜 생성
    private LocalDateTime createDate;
}
