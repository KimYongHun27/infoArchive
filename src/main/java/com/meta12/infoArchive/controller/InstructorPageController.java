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

    @GetMapping("/instructor")
    public String instructorDashboard() {
        return "instructor-dashboard";
    }

    @PostMapping("/instructor/password/change")
    public String changeInstructorPassword(
            Authentication authentication,
            PasswordChangeDto passwordChangeDto
    ) {
        userService.changeMyPassword(authentication, passwordChangeDto);
        return "redirect:/instructor?passwordSuccess=true";
    }
}