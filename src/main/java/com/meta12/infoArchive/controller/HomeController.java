package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

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
}