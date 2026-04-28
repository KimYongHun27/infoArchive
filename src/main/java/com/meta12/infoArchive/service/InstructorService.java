package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.Instructor;
import com.meta12.infoArchive.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepository;

    // 강사 전체 조회
    public List<Instructor> getInstructors() {
        return instructorRepository.findAll();
    }

    // 강사 단건 조회
    public Instructor getInstructor(Long instructorId) {
        return instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("강사를 찾을 수 없습니다."));
    }
}