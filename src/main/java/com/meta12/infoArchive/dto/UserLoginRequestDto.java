package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDto {

    private String email;       // 로그인 이메일
    private String password;    // 로그인 비밀번호
}