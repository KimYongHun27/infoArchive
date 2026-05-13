package com.meta12.infoArchive.dto;

import com.meta12.infoArchive.entity.Role;
import com.meta12.infoArchive.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    private Long id;                    // 회원 고유 번호
    private String username;            // 아이디
    private String name;                // 이름
    private String email;               // 이메일
    private String phone;               // 전화번호
    private Role role;                  // 권한
    private Boolean emailAgree;         // 이메일 수신 동의
    private Boolean smsAgree;           // 문자 수신 동의
    private Boolean pushAgree;          // 푸시 알림 동의
    private LocalDateTime createdAt;    // 가입일

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.role = user.getRole();
        this.emailAgree = user.getEmailAgree();
        this.smsAgree = user.getSmsAgree();
        this.pushAgree = user.getPushAgree();
        this.createdAt = user.getCreatedAt();
    }
}