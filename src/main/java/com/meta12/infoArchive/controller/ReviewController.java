package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.AnswerDto;
import com.meta12.infoArchive.dto.ReviewDto;
import com.meta12.infoArchive.entity.Answer;
import com.meta12.infoArchive.entity.Instructor;
import com.meta12.infoArchive.entity.Review;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.service.AnswerService;
import com.meta12.infoArchive.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/review/taking-course")
    public String list(
    ){
        return "review/taking-course";
    }

    @GetMapping("/review")
    public String review(
            Model model
    ) {
        List<Review> reviewList = reviewService.findAll();
        model.addAttribute("reviewList", reviewList);
        return "review/review";
    }
    @PostMapping("/review/chugaProc")
    public String chugaProc(ReviewDto reviewDto, User user, Instructor instructor) {
        reviewService.chugaProc(reviewDto,user,instructor);
        return "redirect:/review";
    }
    @PostMapping("/review/sujungProc/{id}")
    public String sujungProc(@PathVariable("id") Long id,ReviewDto reviewDto, User user,
                             Instructor instructor) {
        reviewDto.setId(id);
        reviewDto.setNameid(user);
        reviewDto.setNicknameid(instructor);
        reviewService.sujungProc(reviewDto,user,instructor);
        return "redirect:/review";
    }
    @PostMapping("/review/sakjeProc/{id}")
    public String sakjeProc(@PathVariable("id") Long id,
    ReviewDto reviewDto, User user, Instructor instructor) {

        reviewDto.setId(id);
        reviewDto.setNameid(user);
        reviewDto.setNicknameid(instructor);
        reviewService.sakjeProc(reviewDto,user,instructor);
        return "redirect:/review";
    }
//    @GetMapping("/ /chuga")
//    public String chuga(){
//        return "/chuga";
//    }
//
//    @GetMapping("/ /sakje")
//    public String sakje(
//            @PathVariable("id") Long id,
//            ReviewDto reviewDto
//    ){
//        Review review = reviewService.view(id);
//        reviewDto.setId(id);
//        reviewDto.setContent(review.getContent());
//        return "/sakje";
//    }
//    @GetMapping("/ /sujung")
//    public String sujung(
//            @PathVariable("id") Long id,
//            ReviewDto reviewDto
//    ){
//        Review review = reviewService.view(id);
//        reviewDto.setId(id);
//        reviewDto.setContent(review.getContent());
//        return "/sujung";
//    }
//
//
//
//    // Proc
//    @PostMapping("/ /chugaProc")
//    public String chugaProc(){
//        return "redirect:/  /chuga";
//    }
//
//    //    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/ /sujungProc/{id}")
//    public String sujungProc(
//            ReviewDto reviewDto
//    ){
//        return "redirect:/  /view/" + reviewDto.getId();
//    }
//
//    //    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/  /sakjeProc/{id}")
//    public String sakjeProc(){
//        return "redirect:/  /view";
//    }

}
