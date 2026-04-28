package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PurchaseDto {
    private Long id;

    //유저 번호
    private int userId;

    //강의 번호
    private int lectureId;

    //구매 예정일(정렬용)
    private LocalDateTime createAt;
}
