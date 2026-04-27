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

    @PostMapping
    public InstructorApply apply(@RequestBody InstructorApplyRequestDto dto) {
        return applyService.apply(dto);
    }

    @GetMapping
    public List<InstructorApply> getApplications() {
        return applyService.getApplications();
    }

    @PutMapping("/{id}/approve")
    public InstructorApply approve(@PathVariable Long id) {
        return applyService.approve(id);
    }

    @PutMapping("/{id}/reject")
    public InstructorApply reject(@PathVariable Long id, @RequestParam String reason) {
        return applyService.reject(id, reason);
    }
}