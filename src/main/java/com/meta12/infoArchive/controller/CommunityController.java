package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.CommunityDto;
import com.meta12.infoArchive.entity.Community;
import com.meta12.infoArchive.entity.Review;
import com.meta12.infoArchive.service.CommunityService;
import com.meta12.infoArchive.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/community")
      public String community(
              Model model,
              @RequestParam(value="page", defaultValue="0") int page,
              @RequestParam(value="category", defaultValue="all") String category
    )
    {
        Page<Community> paging = communityService.list(page,category);
        model.addAttribute("paging",paging);
        model.addAttribute("category", category);
        return "community/community";
    }

    @GetMapping("/community/edit")
    public String edit()
    {
        return "community/edit";
    }

    @GetMapping("/community/detail/{id}")
    public String detail(
            Model model,
            @PathVariable("id") Long id,
            CommunityDto communityDto
    ) {
        Community community = communityService.detail(id);
        model.addAttribute("community", community);
        return "community/detail";
    }

    @PostMapping("/community/editProc")
    public String editProc(
            CommunityDto communityDto
    )
    {
        communityService.editProc(communityDto);
        return "redirect:/community";
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

