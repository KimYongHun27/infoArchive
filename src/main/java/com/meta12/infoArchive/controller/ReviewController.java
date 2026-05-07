package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.ReviewDto;
import com.meta12.infoArchive.entity.Review;
import com.meta12.infoArchive.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/review/taking-course")
    public String list() {
        return "review/taking-course";
    }

    @GetMapping("/review")
    public String review(Model model) {
        List<Review> reviewList = reviewService.findAll();

        double avgRating = reviewService.getAverageRating();
        long reviewCount = reviewService.getReviewCount();
        int avgRatingPercent = reviewService.getAverageRatingPercent();

        model.addAttribute("reviewList", reviewList);
        model.addAttribute("avgRating", avgRating);
        model.addAttribute("reviewCount", reviewCount);
        model.addAttribute("avgRatingPercent", avgRatingPercent);

        return "mypage/review";
    }

    @PostMapping("/review/chugaProc")
    public String chugaProc(
            Authentication authentication,
            ReviewDto reviewDto
    ) {
        reviewService.chugaProc(authentication, reviewDto);
        return "redirect:/review";
    }

    @PostMapping("/review/sujungProc/{id}")
    public String sujungProc(
            @PathVariable("id") Long id,
            ReviewDto reviewDto
    ) {
        reviewDto.setId(id);
        reviewService.sujungProc(reviewDto);
        return "redirect:/review";
    }

    @PostMapping("/review/sakjeProc/{id}")
    public String sakjeProc(@PathVariable("id") Long id) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(id);
        reviewService.sakjeProc(reviewDto);
        return "redirect:/review";
    }
}