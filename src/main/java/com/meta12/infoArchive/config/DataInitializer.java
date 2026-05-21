package com.meta12.infoArchive.config;

import com.meta12.infoArchive.entity.Role;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {

        fixUsersRoleColumn();

        createOrUpdateUser(
                "user",
                "1234",
                "일반회원",
                "user@test.com",
                "010-1111-1111",
                Role.USER
        );

        createOrUpdateUser(
                "instructor",
                "1234",
                "강사회원",
                "instructor@test.com",
                "010-2222-2222",
                Role.INSTRUCTOR
        );

        createOrUpdateUser(
                "admin",
                "1234",
                "관리자",
                "admin@test.com",
                "010-3333-3333",
                Role.ADMIN
        );

        createOrUpdateUser(
                "securityAdmin",
                "1234",
                "보안관리자",
                "admin2@test.com",
                "010-4444-4444",
                Role.SPECIAL
        );
    }

    private void fixUsersRoleColumn() {
        try {
            jdbcTemplate.execute("ALTER TABLE users MODIFY COLUMN role VARCHAR(30) NOT NULL");
            System.out.println("users.role 컬럼 VARCHAR(30) 수정 완료");
        } catch (Exception e) {
            System.out.println("users.role 컬럼 수정 생략 또는 실패: " + e.getMessage());
        }
    }

    private void createOrUpdateUser(
            String username,
            String rawPassword,
            String name,
            String email,
            String phone,
            Role role
    ) {
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> User.builder()
                        .username(username)
                        .name(name)
                        .email(email)
                        .phone(phone)
                        .role(role)
                        .emailAgree(true)
                        .smsAgree(false)
                        .pushAgree(true)
                        .createdAt(LocalDateTime.now())
                        .membershipActive(false)
                        .enabled(true)
                        .deleted(false)
                        .build()
                );

        if (user.getPassword() == null || !isBCrypt(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(rawPassword));
        }

        user.setUsername(username);
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setRole(role);

        if (user.getEmailAgree() == null) {
            user.setEmailAgree(true);
        }

        if (user.getSmsAgree() == null) {
            user.setSmsAgree(false);
        }

        if (user.getPushAgree() == null) {
            user.setPushAgree(true);
        }

        if (user.getMembershipActive() == null) {
            user.setMembershipActive(false);
        }

        if (user.getEnabled() == null) {
            user.setEnabled(true);
        }

        if (user.getDeleted() == null) {
            user.setDeleted(false);
        }

        userRepository.save(user);
    }

    private boolean isBCrypt(String password) {
        return password != null &&
                (password.startsWith("$2a$")
                        || password.startsWith("$2b$")
                        || password.startsWith("$2y$"));
    }
}