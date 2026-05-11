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

    @GetMapping("/search")
    public String search(
        Model model,
        @RequestParam(value="page", defaultValue="0") int page,
        @RequestParam(value="kw", defaultValue="") String kw) {
        // ReviewService를 사용하여 검색 결과(Page<Review>)를 가져옴
        Page<Review> paging = this.reviewService.getList(page, kw);
        model.addAttribute("paging", paging); // HTML에서 사용할 결과 리스트
        model.addAttribute("kw", kw);         // 검색창에 검색어 유지를 위한 값
        return "search";
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