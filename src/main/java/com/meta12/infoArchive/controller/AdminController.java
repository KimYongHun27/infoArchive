package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.Instructor;
import com.meta12.infoArchive.entity.InstructorApply;
import com.meta12.infoArchive.entity.Role;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // 관리자 - 전체 회원 조회
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    // 관리자 - 전체 강사 조회
    @GetMapping("/instructors")
    public List<Instructor> getAllInstructors() {
        return adminService.getAllInstructors();
    }

    // 관리자 - 강사 신청 목록 조회
    @GetMapping("/applications")
    public List<InstructorApply> getAllApplications() {
        return adminService.getAllApplications();
    }

    // 관리자 - 회원 권한 변경
    @PutMapping("/users/{userId}/role")
    public User changeUserRole(
            @PathVariable Long userId,
            @RequestParam Role role
    ) {
        return adminService.changeUserRole(userId, role);
    }

    // 관리자 - 회원 삭제
    @DeleteMapping("/users/{userId}")
    public String deleteUserByAdmin(@PathVariable Long userId) {
        adminService.deleteUserByAdmin(userId);
        return "관리자 회원 삭제 완료";
    }
}