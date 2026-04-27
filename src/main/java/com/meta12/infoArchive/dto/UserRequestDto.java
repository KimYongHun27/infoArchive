package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    private String username;        // 아이디
    private String password;        // 비밀번호
    private String name;            // 이름
    private String email;           // 이메일
    private String phone;           // 전화번호

    private Boolean emailAgree;     // 이메일 수신 동의
    private Boolean smsAgree;       // 문자 수신 동의
    private Boolean pushAgree;      // 푸시 알림 동의
}