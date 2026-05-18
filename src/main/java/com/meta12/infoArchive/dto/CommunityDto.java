package com.meta12.infoArchive.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityDto {

    private Long id;

    private String title;    // 제목
    private String category; // 카테고리
    private String content;  // 내용
    // 화면 출력용 작성자 이름
    private String nickname;

//    // 작성자
//    @ManyToOne
//    private User author;
}
