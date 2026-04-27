package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private Boolean emailAgree;
    private Boolean smsAgree;
    private Boolean pushAgree;
}