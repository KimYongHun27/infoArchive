package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.PasswordChangeDto;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class InstructorPageController {

    private final UserService userService;

    // 강사 홈
    @GetMapping("/instructor")
    public String instructorDashboard() {
        return "instructor/instructor-dashboard";
    }

    // 내 정보
    @GetMapping("/instructor/my-info")
    public String instructorMyInfo() {
        return "instructor/my-info";
    }

    // 내 강의 관리
    @GetMapping("/instructor/courses")
    public String instructorCourses() {
        return "instructor/courses";
    }

    // 강의 등록
    @GetMapping("/instructor/course-new")
    public String instructorCourseNew() {
        return "instructor/course-new";
    }

    // 강의 등록 처리
    @PostMapping("/instructor/course-new")
    public String instructorCourseCreate() {
        return "redirect:/instructor/courses?courseSuccess=true";
    }

    // 수강생 관리
    @GetMapping("/instructor/students")
    public String instructorStudents() {
        return "instructor/students";
    }

    // 정산 관리
    @GetMapping("/instructor/settlement")
    public String instructorSettlement() {
        return "instructor/settlement";
    }

    // 강사 비밀번호 변경
    @PostMapping("/instructor/password/change")
    public String changeInstructorPassword(
            Authentication authentication,
            PasswordChangeDto passwordChangeDto
    ) {
        userService.changeMyPassword(authentication, passwordChangeDto);
        return "redirect:/instructor/my-info?passwordSuccess=true";
    }
}