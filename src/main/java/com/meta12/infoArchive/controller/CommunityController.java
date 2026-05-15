package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.Review;
import com.meta12.infoArchive.service.CommunityService;
import com.meta12.infoArchive.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommunityController {

    @GetMapping("/community")
      public String community()
    {
        return "community";
    }
}
//    private final CommunityService communityService;
//    @GetMapping("/community")
//    public String community(
//            Model model
//    ){
//        List<Review> community = communityService.findAll();
//        model.addAttribute("community",community);
//        return "community";
//    }

