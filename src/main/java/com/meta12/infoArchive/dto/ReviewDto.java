package com.meta12.infoArchive.dto;

import com.meta12.infoArchive.entity.Instructor;
import com.meta12.infoArchive.entity.User;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDto {

    private Long id;

    // 내용
    private String content;

    // 작성자
    private User author;
    // 강사 작성자 연동
    private Instructor nickname;

    // 날짜 생성
    private LocalDateTime createDate;
}
