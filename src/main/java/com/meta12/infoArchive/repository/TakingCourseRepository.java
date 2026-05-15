package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.TakingCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TakingCourseRepository extends JpaRepository<TakingCourse, Long> {
}
