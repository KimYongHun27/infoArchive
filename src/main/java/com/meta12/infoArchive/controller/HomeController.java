package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping({"", "/"})
    public String index() {
        return "main";
    }

    @GetMapping("/main")
    public String list() {
        return "main";
    }

    @GetMapping("/login")
    public String login() {
        return "login";

    }

    @GetMapping("/top10")
    public String top10() {
        return "top10";


    }

    // 로그인 성공 시 메인페이지로 이동
    @PostMapping("/login")
    public String loginProcess(
            @RequestParam String email,
            @RequestParam String password
    ) {
        try {
            userService.loginByEmail(email, password);
            return "redirect:/main";
        } catch (IllegalArgumentException e) {
            return "redirect:/login";
        }
    }
}