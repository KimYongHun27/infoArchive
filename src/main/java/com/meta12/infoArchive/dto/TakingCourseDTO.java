package com.meta12.infoArchive.dto;

import com.meta12.infoArchive.entity.Course;
import com.meta12.infoArchive.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TakingCourseDTO {
    private Long id;
    private String title;
    private String instructor;
    private String category;

    //유저
    private User user;

    //강의
    private Course course;
    private Integer progressRate = 0;

    //강의 수강일 (정렬용)
    private LocalDateTime createAt;
}
