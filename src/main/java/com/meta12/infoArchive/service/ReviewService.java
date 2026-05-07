package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.ReviewDto;
import com.meta12.infoArchive.entity.Review;
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

    public Review creatEntity(ReviewDto reviewDto){
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setNickname(reviewDto.getNicknameid());
        review.setName(reviewDto.getNameid());
        return review;
    }

    public void chugaProc(ReviewDto reviewDto){
        reviewRepository.save(creatEntity(reviewDto));
    }
    public void sujungProc(ReviewDto reviewDto){
        reviewRepository.save(creatEntity(reviewDto));
    }
    public void sakjeProc(ReviewDto reviewDto){
        reviewRepository.delete(creatEntity(reviewDto));
    }
}