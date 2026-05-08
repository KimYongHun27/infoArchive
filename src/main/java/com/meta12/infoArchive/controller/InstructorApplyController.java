package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.InstructorApplyRequestDto;
import com.meta12.infoArchive.entity.InstructorApply;
import com.meta12.infoArchive.service.InstructorApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class InstructorApplyController {

    private final InstructorApplyService instructorApplyService;

    // =========================
    // 화면용
    // =========================

    // 강사 신청 페이지
    @GetMapping("/instructor/apply")
    public String applyPage() {
        return "instructor-apply";
    }

    // 강사 신청 처리
    @PostMapping("/instructor/apply")
    public String apply(
            Authentication authentication,
            InstructorApplyRequestDto requestDto
    ) {
        instructorApplyService.createInstructorApplication(authentication, requestDto);
        return "redirect:/instructor/apply/complete";
    }

    // 신청 완료 페이지
    @GetMapping("/instructor/apply/complete")
    public String applyComplete() {
        return "instructor-apply-complete";
    }

    // =========================
    // API용
    // =========================

    // 강사 신청 등록 API
    @ResponseBody
    @PostMapping("/api/instructor-applications")
    public InstructorApply createInstructorApplication(
            Authentication authentication,
            @RequestBody InstructorApplyRequestDto requestDto
    ) {
        return instructorApplyService.createInstructorApplication(authentication, requestDto);
    }

    // 강사 신청 전체 조회 API
    @ResponseBody
    @GetMapping("/api/instructor-applications")
    public List<InstructorApply> getApplications() {
        return instructorApplyService.getApplications();
    }

    // 강사 신청 승인 API
    @ResponseBody
    @PutMapping("/api/instructor-applications/{applicationId}/approve")
    public InstructorApply approveApplication(@PathVariable Long applicationId) {
        return instructorApplyService.approveApplication(applicationId);
    }

    // 강사 신청 반려 API
    @ResponseBody
    @PutMapping("/api/instructor-applications/{applicationId}/reject")
    public InstructorApply rejectApplication(
            @PathVariable Long applicationId,
            @RequestParam String rejectReason
    ) {
        return instructorApplyService.rejectApplication(applicationId, rejectReason);
    }
}