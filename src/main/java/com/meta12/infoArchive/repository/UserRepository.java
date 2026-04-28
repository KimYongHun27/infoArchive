package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// 회원 테이블에 접근하는 Repository
public interface UserRepository extends JpaRepository<User, Long> {
}