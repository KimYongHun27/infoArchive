package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Instructor;
import com.meta12.infoArchive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    boolean existsByUser(User user);

    void deleteByUser(User user);
}