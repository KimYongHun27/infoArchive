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

    public List<Review> findAll() {
        return reviewRepository.findAll();
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


//    public Page<Review> getList(int page,String kw){
//        Pageable pageable = PageRequest.of(page,10);
//        Specification<Review> spec = search(kw);
//        return reviewRepository.findAll(spec,pageable);
//    }

//    private Specification<Review> search(String kw) {
//        return (Root<Review> r, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
//            query.distinct(true);
//
//            // 리뷰 작성자(User) 조인
//            Join<Review, User> u = r.join("user", JoinType.LEFT);
//            // 상품 조인
//            Join<Review, Product> p = r.join("product",JoinType.LEFT);
//
//            return cb.or(
//                    cb.like(r.get("title"), "%" + kw + "%"),      // 리뷰 제목
//                    cb.like(r.get("content"), "%" + kw + "%"),    // 리뷰 내용
//                    cb.like(u.get("name"), "%" + kw + "%"),      // 유저 이름
//                    cb.like(p.get("productName"),"%" + kw + "%") // 상품명
//            );
//        };
//    }
}