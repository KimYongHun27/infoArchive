package com.meta12.infoArchive.dto;

import com.meta12.infoArchive.entity.Community;
import com.meta12.infoArchive.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDto {

    private Long id;

    private String content;


    private Community community;

    private User user;
}
