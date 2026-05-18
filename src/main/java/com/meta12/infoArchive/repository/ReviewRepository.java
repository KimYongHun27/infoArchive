package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.Review;
import com.meta12.infoArchive.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select coalesce(avg(r.rating), 0) from Review r")
    Double findAverageRating();

    @Query("select count(r) from Review r")
    Long countAllReviews();

    Page<Review> findAll(Specification<Review> spec, Pageable pageable);

    List<Review> findByProductOrderByCreateDateDesc(Product product);

    List<Review> findByUser(User user);

    void deleteByUser(User user);
}