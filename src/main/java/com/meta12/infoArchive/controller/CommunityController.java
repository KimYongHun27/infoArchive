package com.meta12.infoArchive.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CommunityController {

    @GetMapping("/community")
    public String community(){
        return "community";
    }
}
