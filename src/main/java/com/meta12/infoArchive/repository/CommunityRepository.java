package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    Page<Community> findAll(Pageable pageable);

    Page<Community> findByCategory(String category, Pageable pageable);
}
