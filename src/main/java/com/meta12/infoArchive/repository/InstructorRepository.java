package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

// 강사 테이블에 접근하는 Repository
// 기본 CRUD 기능을 JpaRepository가 자동으로 제공함
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}