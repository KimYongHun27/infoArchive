package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.test;
import com.meta12.infoArchive.service.testService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class testController {
    private final testService testService;

    @GetMapping("/test/list")
    public String list(
            Model model
    ){
        List<test> list = testService.list();
        model.addAttribute("list",list);
        return "test/list";
    }

}
