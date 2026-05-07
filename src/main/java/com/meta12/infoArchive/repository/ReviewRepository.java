package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select coalesce(avg(r.rating), 0) from Review r")
    Double findAverageRating();

    @Query("select count(r) from Review r")
    Long countAllReviews();
}