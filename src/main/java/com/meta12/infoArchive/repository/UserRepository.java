package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 회원 테이블에 접근하는 Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일 중복 확인
    boolean existsByEmail(String email);

    // 아이디 중복 확인
    boolean existsByUsername(String username);

    // 이메일로 회원 찾기
    Optional<User> findByEmail(String email);
}