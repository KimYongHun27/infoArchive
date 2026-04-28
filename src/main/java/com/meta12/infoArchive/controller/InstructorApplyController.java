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

    private final InstructorApplyService instructorApplyService;

    // 강사 신청 등록
    @PostMapping
    public InstructorApply createInstructorApplication(@RequestBody InstructorApplyRequestDto requestDto) {
        return instructorApplyService.createInstructorApplication(requestDto);
    }

    // 강사 신청 전체 조회
    @GetMapping
    public List<InstructorApply> getApplications() {
        return instructorApplyService.getApplications();
    }

    // 강사 신청 승인
    @PutMapping("/{applicationId}/approve")
    public InstructorApply approveApplication(@PathVariable Long applicationId) {
        return instructorApplyService.approveApplication(applicationId);
    }

    // 강사 신청 반려
    @PutMapping("/{applicationId}/reject")
    public InstructorApply rejectApplication(
            @PathVariable Long applicationId,
            @RequestParam String rejectReason
    ) {
        return instructorApplyService.rejectApplication(applicationId, rejectReason);
    }
}