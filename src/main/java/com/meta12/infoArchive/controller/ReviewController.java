package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.ReviewDto;
import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.Review;
import com.meta12.infoArchive.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.service.ProductService;
import com.meta12.infoArchive.service.UserService;
import com.meta12.infoArchive.entity.User;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/review/taking-course")
    public String list() {
        return "review/taking-course";
    }

    @GetMapping("/review")
    public String review(
            Model model,
            Authentication authentication
    ) {
        List<Review> reviewList = reviewService.findMyReviews(authentication);

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
            ReviewDto reviewDto,
            Authentication authentication
    ) {
        reviewDto.setId(id);
        reviewService.sujungProc(reviewDto, authentication);
        return "redirect:/review";
    }

    @PostMapping("/review/sakjeProc/{id}")
    public String sakjeProc(
            @PathVariable("id") Long id,
            Authentication authentication
    ) {
        reviewService.sakjeProc(id, authentication);
        return "redirect:/review";
    }

    @PostMapping("/product/{id}/reviews")
    public String createReview(
            @PathVariable Long id,
            @RequestParam int rating,
            @RequestParam String content,
            Authentication authentication
    ) {
        Product product = productService.getProduct(id);
        User user = userService.getLoginUser(authentication);

        reviewService.createReview(product, user, rating, content);

        return "redirect:/product/" + id;
    }
}