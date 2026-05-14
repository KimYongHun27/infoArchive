package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
