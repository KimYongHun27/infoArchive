package com.meta12.infoArchive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"", "/"})
    public String index() {
        return "index/index"; //포워딩
        //return "redirect:/question/list";
    }

}
