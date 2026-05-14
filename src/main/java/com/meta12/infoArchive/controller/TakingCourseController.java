package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.service.TakingCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class TakingCourseController {

    private final TakingCourseService takingCourseService;

}