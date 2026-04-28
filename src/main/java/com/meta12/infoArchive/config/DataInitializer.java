package com.meta12.infoArchive.config;

import com.meta12.infoArchive.entity.Role;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {

        // 일반 회원 계정
        if (!userRepository.existsByEmail("user@test.com")) {
            User user = User.builder()
                    .username("user")
                    .password("1234")
                    .name("일반회원")
                    .email("user@test.com")
                    .phone("010-1111-1111")
                    .role(Role.USER)
                    .emailAgree(true)
                    .smsAgree(false)
                    .pushAgree(true)
                    .createdAt(LocalDateTime.now())
                    .build();

            userRepository.save(user);
        }

        // 강사 계정
        if (!userRepository.existsByEmail("instructor@test.com")) {
            User instructor = User.builder()
                    .username("instructor")
                    .password("1234")
                    .name("강사회원")
                    .email("instructor@test.com")
                    .phone("010-2222-2222")
                    .role(Role.INSTRUCTOR)
                    .emailAgree(true)
                    .smsAgree(true)
                    .pushAgree(true)
                    .createdAt(LocalDateTime.now())
                    .build();

            userRepository.save(instructor);
        }

        // 관리자 계정
        if (!userRepository.existsByEmail("admin@test.com")) {
            User admin = User.builder()
                    .username("admin")
                    .password("1234")
                    .name("관리자")
                    .email("admin@test.com")
                    .phone("010-3333-3333")
                    .role(Role.ADMIN)
                    .emailAgree(false)
                    .smsAgree(false)
                    .pushAgree(false)
                    .createdAt(LocalDateTime.now())
                    .build();

            userRepository.save(admin);
        }
    }
}