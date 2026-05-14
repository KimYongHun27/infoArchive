package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.TakingCourse;
import com.meta12.infoArchive.repository.TakingCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TakingCourseService {
    private final TakingCourseRepository takingCourseRepository;

}
