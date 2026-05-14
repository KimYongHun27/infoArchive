package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.Course;
import com.meta12.infoArchive.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public Course getCourse(Long id)
    {
        Optional<Course> optional = courseRepository.findById(id);
        Course course = null;

        if (optional.isPresent())
        {
            course = optional.get();
        }

        return course;
    }
}
