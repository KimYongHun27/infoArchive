package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.ReviewDto;
import com.meta12.infoArchive.entity.Instructor;
import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.Review;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.ReviewRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;

    public List<Review> findMyReviews(Authentication authentication) {
        User user = userService.getLoginUser(authentication);
        return reviewRepository.findByUser(user);
    }

    public void chugaProc(Authentication authentication, ReviewDto reviewDto) {

        User user = userService.getLoginUser(authentication);

        Review review = new Review();

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setRating(reviewDto.getRating());
        review.setCreateDate(LocalDateTime.now());
        review.setUser(user);

        reviewRepository.save(review);
    }

    public void sujungProc(ReviewDto reviewDto) {
        Review review = reviewRepository.findById(reviewDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setRating(reviewDto.getRating());

        reviewRepository.save(review);
    }

    public void sakjeProc(ReviewDto reviewDto) {
        Review review = reviewRepository.findById(reviewDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        reviewRepository.delete(review);
    }

    public double getAverageRating() {
        Double avg = reviewRepository.findAverageRating();

        if (avg == null) {
            return 0.0;
        }

        return Math.round(avg * 10.0) / 10.0; // 소수점 1자리
    }

    public long getReviewCount() {
        Long count = reviewRepository.countAllReviews();
        return count != null ? count : 0;
    }

    public int getAverageRatingPercent() {
        double avg = getAverageRating();
        return (int) Math.round((avg / 5.0) * 100);
    }

    // 상품 상세페이지 - 해당 상품 리뷰 조회
    public List<Review> findByProduct(Product product) {
        return reviewRepository.findByProductOrderByCreateDateDesc(product);
    }

    // 상품 상세페이지 - 리뷰 등록
    public void createReview(Product product, User user, int rating, String content) {

        if (product == null) {
            throw new IllegalArgumentException("상품을 찾을 수 없습니다.");
        }

        if (user == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("평점은 1점부터 5점까지 입력할 수 있습니다.");
        }

        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("리뷰 내용을 입력해주세요.");
        }

        Review review = new Review();

        review.setProduct(product);
        review.setUser(user);
        review.setRating(rating);
        review.setContent(content.trim());
        review.setCreateDate(LocalDateTime.now());

        reviewRepository.save(review);
    }
}