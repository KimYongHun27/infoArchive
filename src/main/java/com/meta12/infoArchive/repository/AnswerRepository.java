package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    void deleteByUserId(Long userId);
}