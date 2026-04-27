package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.UserRequestDto;
import com.meta12.infoArchive.entity.Role;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원 등록
    public User createUser(UserRequestDto dto) {
        User user = User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .role(Role.USER)
                .emailAgree(dto.getEmailAgree())
                .smsAgree(dto.getSmsAgree())
                .pushAgree(dto.getPushAgree())
                .createdAt(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }

    // 회원 전체 조회
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // 회원 단건 조회
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }

}