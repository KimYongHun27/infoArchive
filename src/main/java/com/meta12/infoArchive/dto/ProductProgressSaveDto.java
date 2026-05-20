package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductProgressSaveDto {

    private Long productId;

    private Integer watchedSeconds;

    private Integer totalSeconds;
}