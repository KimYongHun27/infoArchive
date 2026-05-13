package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.Instructor;
import com.meta12.infoArchive.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/instructors")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    // 강사 전체 조회 API
    @GetMapping
    public List<Instructor> getInstructors() {
        return instructorService.getInstructors();
    }

    // 강사 단건 조회 API
    @GetMapping("/{instructorId}")
    public Instructor getInstructor(@PathVariable Long instructorId) {
        return instructorService.getInstructor(instructorId);
    }
}