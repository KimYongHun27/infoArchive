package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

}
