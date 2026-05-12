package com.meta12.infoArchive.config;

import com.meta12.infoArchive.entity.Role;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

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
                "special",
                "1234",
                "특별계정",
                "admin2@test.com",
                "010-4444-4444",
                Role.SPECIAL
        );
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
                        .build()
                );

        // 기존 비밀번호가 평문이면 자동 암호화
        if (user.getPassword() == null || !isBCrypt(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(rawPassword));
        }

        user.setRole(role);
        userRepository.save(user);
    }

    private boolean isBCrypt(String password) {
        return password.startsWith("$2a$")
                || password.startsWith("$2b$")
                || password.startsWith("$2y$");
    }
}