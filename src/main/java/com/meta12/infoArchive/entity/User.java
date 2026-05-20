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

    @Column(unique = true)
    private String username;         // 로그인 아이디

    @Column(nullable = false)
    private String password;         // 비밀번호

    private String name;             // 이름

    @Column(nullable = false, unique = true)
    private String email;            // 이메일

    private String phone;            // 전화번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Role role;               // 회원 권한

    private Boolean emailAgree;      // 이메일 수신 동의
    private Boolean smsAgree;        // 문자 수신 동의
    private Boolean pushAgree;       // 푸시 알림 동의

    private LocalDateTime createdAt; // 가입일

    // 계정 활성화 여부
    private Boolean enabled = true;

    // 관리자 삭제/탈퇴 처리 여부
    private Boolean deleted = false;

    // 멤버십 활성 여부
    private Boolean membershipActive;

    // 멤버십 시작일
    private LocalDateTime membershipStartedAt;

    // 멤버십 만료일
    private LocalDateTime membershipExpiredAt;

    @PrePersist
    public void prePersist() {

        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }

        if (this.role == null) {
            this.role = Role.USER;
        }

        if (this.emailAgree == null) {
            this.emailAgree = false;
        }

        if (this.smsAgree == null) {
            this.smsAgree = false;
        }

        if (this.pushAgree == null) {
            this.pushAgree = false;
        }

        if (this.membershipActive == null) {
            this.membershipActive = false;
        }

        if (this.enabled == null) {
            this.enabled = true;
        }

        if (this.deleted == null) {
            this.deleted = false;
        }
    }
}