package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.UserRequestDto;
import com.meta12.infoArchive.entity.Role;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.meta12.infoArchive.dto.UserLoginRequestDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원 등록
    public User createUser(UserRequestDto requestDto) {

        User newUser = User.builder()
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .phone(requestDto.getPhone())
                .role(Role.USER)
                .emailAgree(requestDto.getEmailAgree())
                .smsAgree(requestDto.getSmsAgree())
                .pushAgree(requestDto.getPushAgree())
                .createdAt(LocalDateTime.now())
                .build();

        return userRepository.save(newUser);
    }

    // 회원 전체 조회
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // 회원 단건 조회
    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }

    // 회원 정보 수정
    public User updateUser(Long userId, UserRequestDto requestDto) {

        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        foundUser.setName(requestDto.getName());
        foundUser.setEmail(requestDto.getEmail());
        foundUser.setPhone(requestDto.getPhone());
        foundUser.setEmailAgree(requestDto.getEmailAgree());
        foundUser.setSmsAgree(requestDto.getSmsAgree());
        foundUser.setPushAgree(requestDto.getPushAgree());

        return userRepository.save(foundUser);
    }

    // 회원 삭제
    public void deleteUser(Long userId) {

        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        userRepository.delete(foundUser);
    }

    // 회원 로그인
    public User login(UserLoginRequestDto requestDto) {

        User foundUser = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 틀렸습니다."));

        if (!foundUser.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 틀렸습니다.");
        }

        return foundUser;
    }
}