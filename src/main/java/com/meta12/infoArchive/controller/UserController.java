package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.UserRequestDto;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.meta12.infoArchive.dto.UserLoginRequestDto;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 등록
    @PostMapping
    public User createUser(@RequestBody UserRequestDto requestDto) {
        return userService.createUser(requestDto);
    }

    // 회원 전체 조회
    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    // 회원 단건 조회
    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    // 회원 정보 수정
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody UserRequestDto requestDto) {
        return userService.updateUser(userId, requestDto);
    }

    // 회원 삭제
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return "회원 삭제 완료";
    }

    // 회원 로그인
    @PostMapping("/login")
    public User login(@RequestBody UserLoginRequestDto requestDto) {
        return userService.login(requestDto);
    }
}