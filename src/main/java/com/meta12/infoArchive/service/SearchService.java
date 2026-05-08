package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.Instructor;
import com.meta12.infoArchive.entity.Review;
import com.meta12.infoArchive.entity.User;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class SearchService {

    private Specification<Review> search(String kw) {
        return (Root<Review> r, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            query.distinct(true);

            // 리뷰 작성자(User) 조인
            Join<Review, User> u = r.join("name", JoinType.LEFT);

            // 강사(Instructor) 조인
            Join<Review, Instructor> i = r.join("nickname", JoinType.LEFT);

            return cb.or(
                    cb.like(r.get("title"), "%" + kw + "%"),      // 리뷰 제목
                    cb.like(r.get("content"), "%" + kw + "%"),    // 리뷰 내용
                    cb.like(u.get("username"), "%" + kw + "%"),   // 유저 아이디 (User 엔티티의 username 필드)
                    cb.like(i.get("nickname"), "%" + kw + "%")    // 강사 닉네임 (Instructor 엔티티의 nickname 필드)
            );
        };
    }
}
