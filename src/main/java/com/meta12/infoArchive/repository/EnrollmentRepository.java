package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByUserIdAndProductId(Long userId, Long productId);

    List<Enrollment> findByUserIdOrderByEnrolledAtDesc(Long userId);

    Optional<Enrollment> findByUserIdAndProductId(Long userId, Long productId);
}