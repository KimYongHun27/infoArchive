package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // 아이디 중복 확인
    boolean existsByUsername(String username);
}