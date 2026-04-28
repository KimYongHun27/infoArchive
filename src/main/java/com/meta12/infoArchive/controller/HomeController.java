package com.meta12.infoArchive.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping({"", "/"})
    public String index() {
        return "main";
    }

    @GetMapping("/main")
    public String list(){
        return "main";
    }

    // 로그인 화면
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}


