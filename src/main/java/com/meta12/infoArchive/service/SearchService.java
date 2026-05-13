package com.meta12.infoArchive.service;

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

@Service
@RequiredArgsConstructor

public class SearchService {

    private final ReviewRepository reviewRepository;

    public Page<Review> getList(int page, String kw){
        Pageable pageable = PageRequest.of(page,10);
        Specification<Review> spec = search(kw);
        return reviewRepository.findAll(spec,pageable);
    }

    private Specification<Review> search(String kw) {
        return (Root<Review> r, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            query.distinct(true);

            // 리뷰 작성자(User) 조인
            Join<Review, User> u = r.join("user", JoinType.LEFT);
            // 상품 조인
            Join<Review, Product> p = r.join("product",JoinType.LEFT);

            return cb.or(
                    cb.like(r.get("title"), "%" + kw + "%"),      // 리뷰 제목
                    cb.like(r.get("content"), "%" + kw + "%"),    // 리뷰 내용
                    cb.like(u.get("name"), "%" + kw + "%"),      // 유저 이름
                    cb.like(p.get("productName"),"%" + kw + "%") // 상품명
            );
        };
    }
}
