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

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/review")
    public String review() {
        return "/review/review";
    }
    @GetMapping("/front10")
    public String front10() {
        return "/review/front10";
    }
}


