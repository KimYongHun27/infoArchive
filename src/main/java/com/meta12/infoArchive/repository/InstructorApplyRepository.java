package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.InstructorApply;
import org.springframework.data.jpa.repository.JpaRepository;

// 강사 신청 테이블에 접근하는 Repository
public interface InstructorApplyRepository extends JpaRepository<InstructorApply, Long> {
}