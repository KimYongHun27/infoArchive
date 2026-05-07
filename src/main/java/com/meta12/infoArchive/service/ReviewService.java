package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.ReviewDto;
import com.meta12.infoArchive.entity.Instructor;
import com.meta12.infoArchive.entity.Review;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Review view(Long id){
        Optional<Review> optionalReview = reviewRepository.findById(id);
        Review review = null;
        if (optionalReview.isPresent()){
            review = optionalReview.get();
        }
        return review;
    }

    public Review creatEntity(ReviewDto reviewDto, User user, Instructor instructor){
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setName(user);
        review.setNickname(instructor);
        return review;
    }

    public void chugaProc(ReviewDto reviewDto, User user, Instructor instructor){
        reviewRepository.save(creatEntity(reviewDto,user,instructor));
    }
    public void sujungProc(ReviewDto reviewDto, User user, Instructor instructor){
        reviewRepository.save(creatEntity(reviewDto,user,instructor));
    }
    public void sakjeProc(ReviewDto reviewDto, User user, Instructor instructor){
        reviewRepository.delete(creatEntity(reviewDto,user,instructor));
    }
}