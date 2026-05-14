package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CartDto {
    private Long id;
    private Long userId;
    private Long courseId;
    private LocalDateTime createdAt;
}
