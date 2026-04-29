package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.ReviewDto;
import com.meta12.infoArchive.entity.Review;
import com.meta12.infoArchive.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Review view(Long id){
        Optional<Review> optionalReview = reviewRepository.findById(id);
        Review review = null;
        if (optionalReview.isPresent()){
            review = optionalReview.get();
        }
        return review;
    }

    public Review chugaProc(ReviewDto reviewDto){
        Review review = new Review();
        review.setContent(reviewDto.getContent());
        review.setAuthor(reviewDto.getAuthor());
        review.setNickname(reviewDto.getNickname());
        review.setCreateDate(LocalDateTime.now());
        reviewRepository.save(review);
        return review;
    }
    public Review sujungProc(ReviewDto reviewDto){
        Review review = new Review();
        review.setContent(reviewDto.getContent());
        review.setAuthor(reviewDto.getAuthor());
        review.setNickname(reviewDto.getNickname());
        review.setCreateDate(LocalDateTime.now());
        reviewRepository.save(review);
        return review;
    }

    public Review sakjeProc(ReviewDto reviewDto){
        Review review = new Review();
        review.setContent(reviewDto.getContent());
        review.setAuthor(reviewDto.getAuthor());
        review.setNickname(reviewDto.getNickname());
        review.setCreateDate(LocalDateTime.now());
        reviewRepository.delete(review);
        return review;
    }

}
