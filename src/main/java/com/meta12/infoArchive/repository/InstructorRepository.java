package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

// 강사 테이블에 접근하는 Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}