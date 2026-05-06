package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfoUpdateDto {

    private String name;
    private String phone;

    private Boolean emailAgree;
    private Boolean smsAgree;
    private Boolean pushAgree;
}