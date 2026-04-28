package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.InstructorApplyRequestDto;
import com.meta12.infoArchive.entity.ApplyStatus;
import com.meta12.infoArchive.entity.Instructor;
import com.meta12.infoArchive.entity.InstructorApply;
import com.meta12.infoArchive.entity.Role;
import com.meta12.infoArchive.entity.User;
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

    private final InstructorApplyRepository instructorApplyRepository;
    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;

    // 강사 신청 등록
    public InstructorApply createInstructorApplication(InstructorApplyRequestDto requestDto) {

        User foundUser = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        InstructorApply newApplication = InstructorApply.builder()
                .user(foundUser)
                .title(requestDto.getTitle())
                .intro(requestDto.getIntro())
                .career(requestDto.getCareer())
                .portfolioUrl(requestDto.getPortfolioUrl())
                .status(ApplyStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        return instructorApplyRepository.save(newApplication);
    }

    // 강사 신청 전체 조회
    public List<InstructorApply> getApplications() {
        return instructorApplyRepository.findAll();
    }

    // 강사 신청 승인
    public InstructorApply approveApplication(Long applicationId) {

        InstructorApply foundApplication = instructorApplyRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("신청서를 찾을 수 없습니다."));

        foundApplication.setStatus(ApplyStatus.APPROVED);
        foundApplication.setReviewedAt(LocalDateTime.now());

        User applicantUser = foundApplication.getUser();
        applicantUser.setRole(Role.INSTRUCTOR);

        Instructor newInstructor = Instructor.builder()
                .user(applicantUser)
                .nickname(applicantUser.getName())
                .intro(foundApplication.getIntro())
                .career(foundApplication.getCareer())
                .category(foundApplication.getTitle())
                .createdAt(LocalDateTime.now())
                .build();

        instructorRepository.save(newInstructor);
        userRepository.save(applicantUser);

        return instructorApplyRepository.save(foundApplication);
    }

    // 강사 신청 반려
    public InstructorApply rejectApplication(Long applicationId, String rejectReason) {

        InstructorApply foundApplication = instructorApplyRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("신청서를 찾을 수 없습니다."));

        foundApplication.setStatus(ApplyStatus.REJECTED);
        foundApplication.setRejectReason(rejectReason);
        foundApplication.setReviewedAt(LocalDateTime.now());

        return instructorApplyRepository.save(foundApplication);
    }
}