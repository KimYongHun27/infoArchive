package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.InstructorApplyRequestDto;
import com.meta12.infoArchive.entity.*;
import com.meta12.infoArchive.repository.InstructorApplyRepository;
import com.meta12.infoArchive.repository.InstructorRepository;
import com.meta12.infoArchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorApplyService {

    private final InstructorApplyRepository applyRepository;
    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;

    // 강사 신청 등록
    public InstructorApply apply(InstructorApplyRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        InstructorApply apply = InstructorApply.builder()
                .user(user)
                .title(dto.getTitle())
                .intro(dto.getIntro())
                .career(dto.getCareer())
                .portfolioUrl(dto.getPortfolioUrl())
                .status(ApplyStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        return applyRepository.save(apply);
    }

    // 강사 신청 전체 조회
    public List<InstructorApply> getApplications() {
        return applyRepository.findAll();
    }


    // 강사 신청 승인
    public InstructorApply approve(Long applyId) {
        InstructorApply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new IllegalArgumentException("신청서를 찾을 수 없습니다."));

        apply.setStatus(ApplyStatus.APPROVED);
        apply.setReviewedAt(LocalDateTime.now());

        User user = apply.getUser();
        user.setRole(Role.INSTRUCTOR);

        Instructor instructor = Instructor.builder()
                .user(user)
                .nickname(user.getName())
                .intro(apply.getIntro())
                .career(apply.getCareer())
                .category(apply.getTitle())
                .createdAt(LocalDateTime.now())
                .build();

        instructorRepository.save(instructor);

        return applyRepository.save(apply);
    }

    // 강사 신청 반려
    public InstructorApply reject(Long applyId, String reason) {
        InstructorApply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new IllegalArgumentException("신청서를 찾을 수 없습니다."));

        apply.setStatus(ApplyStatus.REJECTED);
        apply.setRejectReason(reason);
        apply.setReviewedAt(LocalDateTime.now());

        return applyRepository.save(apply);
    }
}