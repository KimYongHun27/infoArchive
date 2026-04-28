package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                 // 회원 고유 번호

    private String username;         // 로그인 아이디
    private String password;         // 비밀번호
    private String name;             // 이름
    private String email;            // 이메일
    private String phone;            // 전화번호

    @Enumerated(EnumType.STRING)
    private Role role;               // 회원 권한

    private Boolean emailAgree;      // 이메일 수신 동의
    private Boolean smsAgree;        // 문자 수신 동의
    private Boolean pushAgree;       // 푸시 알림 동의

    private LocalDateTime createdAt; // 가입일
}