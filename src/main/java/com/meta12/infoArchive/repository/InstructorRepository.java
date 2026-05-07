package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Instructor;
import com.meta12.infoArchive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// 강사 테이블에 접근하는 Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    // 해당 회원이 이미 강사로 등록되어 있는지 확인
    boolean existsByUser(User user);
}