package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.InstructorApplyRequestDto;
import com.meta12.infoArchive.entity.InstructorApply;
import com.meta12.infoArchive.service.InstructorApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructor-applications")
@RequiredArgsConstructor
public class InstructorApplyController {

    private final InstructorApplyService applyService;

    // 강사 신청 등록
    @PostMapping
    public InstructorApply apply(@RequestBody InstructorApplyRequestDto dto) {
        return applyService.apply(dto);
    }

    // 강사 신청 전체 조회
    @GetMapping
    public List<InstructorApply> getApplications() {
        return applyService.getApplications();
    }

    // 강사 신청 승인
    @PutMapping("/{id}/approve")
    public InstructorApply approve(@PathVariable Long id) {
        return applyService.approve(id);
    }

    // 강사 신청 반려
    @PutMapping("/{id}/reject")
    public InstructorApply reject(@PathVariable Long id, @RequestParam String reason) {
        return applyService.reject(id, reason);
    }
}