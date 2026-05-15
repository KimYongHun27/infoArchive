package com.meta12.infoArchive.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityDto {

    private Long id;

    private String title;

    private String content;

//    // 작성자
//    @ManyToOne
//    private User author;
}
