package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
