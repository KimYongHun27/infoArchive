package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.Review;
import com.meta12.infoArchive.service.ReviewService;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final ReviewService reviewService;

    @GetMapping({"", "/"})
    public String index() {
        return "main";
    }

    @GetMapping("/main")
    public String list() {
        return "main";
    }

    @GetMapping("/top10")
    public String top10() {
        return "top10";

    }

    @GetMapping("/category/{categoryName}")
    public String categoryPage(
            @PathVariable String categoryName,
            Model model
    ) {
        model.addAttribute("courses", new ArrayList<>());

        return "category/" + categoryName;
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "mypage/dashboard";
    }
}