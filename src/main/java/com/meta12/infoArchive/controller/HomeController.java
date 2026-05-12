package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.service.ReviewService;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final ReviewService reviewService;

    private static final Set<String> ALLOWED_CATEGORIES = Set.of(
            "cooking",
            "craft",
            "design",
            "drawing",
            "finance",
            "fitness",
            "language",
            "music",
            "photo-video",
            "programing",
            "video-3d"
    );

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
        if (!ALLOWED_CATEGORIES.contains(categoryName)) {
            return "redirect:/main";
        }

        model.addAttribute("categoryName", categoryName);
        model.addAttribute("courses", new ArrayList<>());

        return "category/" + categoryName;
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "mypage/dashboard";
    }
}