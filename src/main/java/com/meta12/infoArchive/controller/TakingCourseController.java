package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.service.TakingCourseService;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor

public class TakingCourseController {
    private final TakingCourseService takingCourseService;
    private final UserService userService;

    //전체보기
    @GetMapping("/takingcourse")
    public String view() {
        return "mypage/taking-course";
    }
}