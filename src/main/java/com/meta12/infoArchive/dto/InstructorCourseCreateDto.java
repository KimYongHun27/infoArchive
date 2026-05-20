package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorCourseCreateDto {

    private String title;
    private String category;
    private int price;
    private Integer discountRate;
    private String thumbnailUrl;
    private String videoUrl;
    private String description;
}