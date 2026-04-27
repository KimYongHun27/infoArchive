package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}