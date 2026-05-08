package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.ApplyStatus;
import com.meta12.infoArchive.entity.InstructorApply;
import com.meta12.infoArchive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstructorApplyRepository extends JpaRepository<InstructorApply, Long> {

    boolean existsByUserAndStatus(User user, ApplyStatus status);

    Optional<InstructorApply> findTopByUserOrderByCreatedAtDesc(User user);
}