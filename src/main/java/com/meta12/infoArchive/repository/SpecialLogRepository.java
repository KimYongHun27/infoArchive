package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.SpecialLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecialLogRepository extends JpaRepository<SpecialLog, Long> {

    List<SpecialLog> findTop20ByOrderByExecutedAtDesc();

    List<SpecialLog> findTop5ByOrderByExecutedAtDesc();

    long countByStatus(String status);
}