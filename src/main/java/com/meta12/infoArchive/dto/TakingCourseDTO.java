package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TakingCourseDTO {
    private String title;
    private String instructor;
    private String category;
    private Integer progressRate;
    private String thumbnailUrl;
}
