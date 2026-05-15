package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TakingCourseDto {
    private Long id;

    //유저 번호
    private Long userId;

    //강의 번호
    private Long lectureId;

    //강의 수강일 (정렬용)
    private LocalDateTime createAt;
}