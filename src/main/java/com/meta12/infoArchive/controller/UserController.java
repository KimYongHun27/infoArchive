package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.UserRequestDto;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 등록
    @PostMapping
    public User createUser(@RequestBody UserRequestDto dto) {
        return userService.createUser(dto);
    }

    // 회원 전체 조회
    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    // 회원 단건 조회
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}