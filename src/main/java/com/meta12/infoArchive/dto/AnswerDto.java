package com.meta12.infoArchive.dto;

import com.meta12.infoArchive.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDto {

    private Long id;

    // 내용
    private String content;

    // 작성자
    private User author;
}
