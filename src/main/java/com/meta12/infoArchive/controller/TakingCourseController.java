package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.Enrollment;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.EnrollmentRepository;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TakingCourseController {

    private final UserService userService;
    private final EnrollmentRepository enrollmentRepository;

    @GetMapping("/taking-course")
    public String view(Authentication authentication, Model model) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        User user = userService.getLoginUser(authentication);

        List<Enrollment> enrollmentList =
                enrollmentRepository.findByUserIdOrderByEnrolledAtDesc(user.getId());

        model.addAttribute("enrollmentList", enrollmentList);

        return "mypage/taking-course";
    }
}