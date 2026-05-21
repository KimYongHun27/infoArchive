package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.ReviewDto;
import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.Review;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;

    /**
     * 리뷰 내용 정리
     * - 엔터 줄바꿈은 유지
     * - 윈도우 줄바꿈(\r\n), 맥 줄바꿈(\r)을 \n으로 통일
     * - 앞뒤 공백만 제거
     * - 내용이 비어 있으면 예외 발생
     */
    private String normalizeReviewContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("리뷰 내용을 입력해주세요.");
        }

        return content
                .replace("\r\n", "\n")
                .replace("\r", "\n")
                .trim();
    }

    /**
     * 내가 작성한 리뷰 조회
     */
    public List<Review> findMyReviews(Authentication authentication) {
        User user = userService.getLoginUser(authentication);
        return reviewRepository.findByUser(user);
    }

    /**
     * 마이페이지 리뷰 등록
     */
    public void chugaProc(Authentication authentication, ReviewDto reviewDto) {

        User user = userService.getLoginUser(authentication);

        if (reviewDto.getRating() < 1 || reviewDto.getRating() > 5) {
            throw new IllegalArgumentException("평점은 1점부터 5점까지 입력할 수 있습니다.");
        }

        Review review = new Review();

        review.setTitle(reviewDto.getTitle());
        review.setContent(normalizeReviewContent(reviewDto.getContent()));
        review.setRating(reviewDto.getRating());
        review.setCreateDate(LocalDateTime.now());
        review.setUser(user);

        reviewRepository.save(review);
    }

    /**
     * 마이페이지 리뷰 수정
     */
    public void sujungProc(ReviewDto reviewDto, Authentication authentication) {

        User loginUser = userService.getLoginUser(authentication);

        Review review = reviewRepository.findById(reviewDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        if (review.getUser() == null || !review.getUser().getId().equals(loginUser.getId())) {
            throw new IllegalArgumentException("본인이 작성한 리뷰만 수정할 수 있습니다.");
        }

        if (reviewDto.getRating() < 1 || reviewDto.getRating() > 5) {
            throw new IllegalArgumentException("평점은 1점부터 5점까지 입력할 수 있습니다.");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(normalizeReviewContent(reviewDto.getContent()));
        review.setRating(reviewDto.getRating());

        reviewRepository.save(review);
    }

    /**
     * 마이페이지 리뷰 삭제
     */
    public void sakjeProc(Long id, Authentication authentication) {

        User loginUser = userService.getLoginUser(authentication);

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        if (review.getUser() == null || !review.getUser().getId().equals(loginUser.getId())) {
            throw new IllegalArgumentException("본인이 작성한 리뷰만 삭제할 수 있습니다.");
        }

        reviewRepository.delete(review);
    }

    /**
     * 전체 평균 평점
     */
    public double getAverageRating() {
        Double avg = reviewRepository.findAverageRating();

        if (avg == null) {
            return 0.0;
        }

        return Math.round(avg * 10.0) / 10.0;
    }

    /**
     * 전체 리뷰 개수
     */
    public long getReviewCount() {
        Long count = reviewRepository.countAllReviews();
        return count != null ? count : 0;
    }

    /**
     * 평균 평점 퍼센트
     */
    public int getAverageRatingPercent() {
        double avg = getAverageRating();
        return (int) Math.round((avg / 5.0) * 100);
    }

    /**
     * 상품 상세페이지 - 해당 상품 리뷰 조회
     */
    public List<Review> findByProduct(Product product) {
        return reviewRepository.findByProductOrderByCreateDateDesc(product);
    }

    /**
     * 상품 상세페이지 - 리뷰 등록
     */
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

        Review review = new Review();

        review.setProduct(product);
        review.setUser(user);
        review.setRating(rating);
        review.setContent(normalizeReviewContent(content));
        review.setCreateDate(LocalDateTime.now());

        reviewRepository.save(review);
    }
}