package com.meta12.infoArchive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InstructorPageController {

    @GetMapping("/instructor")
    public String instructorDashboard() {
        return "instructor-dashboard";
    }
}