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
import org.springframework.security.core.Authentication;
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
    public InstructorApply createInstructorApplication(
            Authentication authentication,
            InstructorApplyRequestDto requestDto
    ) {
        String email = authentication.getName();

        User foundUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("로그인 회원을 찾을 수 없습니다."));

        if (foundUser.getRole() == Role.INSTRUCTOR) {
            throw new IllegalArgumentException("이미 강사 회원입니다.");
        }

        if (foundUser.getRole() == Role.INSTRUCTOR_PENDING) {
            throw new IllegalArgumentException("이미 강사 신청 검토 중입니다.");
        }

        InstructorApply newApplication = InstructorApply.builder()
                .user(foundUser)
                .title(requestDto.getTitle())
                .intro(requestDto.getIntro())
                .career(requestDto.getCareer())
                .portfolioUrl(requestDto.getPortfolioUrl())
                .status(ApplyStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        InstructorApply savedApplication = instructorApplyRepository.save(newApplication);

        foundUser.setRole(Role.INSTRUCTOR_PENDING);
        userRepository.save(foundUser);

        return savedApplication;
    }

    // 강사 신청 전체 조회
    public List<InstructorApply> getApplications() {
        return instructorApplyRepository.findAll();
    }

    // 강사 신청 승인
    public InstructorApply approveApplication(Long applicationId) {

        InstructorApply foundApplication = instructorApplyRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("신청서를 찾을 수 없습니다."));

        if (foundApplication.getStatus() == ApplyStatus.APPROVED) {
            throw new IllegalArgumentException("이미 승인된 신청서입니다.");
        }

        if (foundApplication.getStatus() == ApplyStatus.REJECTED) {
            throw new IllegalArgumentException("이미 반려된 신청서입니다.");
        }

        foundApplication.setStatus(ApplyStatus.APPROVED);
        foundApplication.setReviewedAt(LocalDateTime.now());
        foundApplication.setRejectReason(null);

        User applicantUser = foundApplication.getUser();
        applicantUser.setRole(Role.INSTRUCTOR);

        // 이미 강사 테이블에 등록된 회원이면 중복 생성 방지
        if (!instructorRepository.existsByUser(applicantUser)) {
            Instructor newInstructor = Instructor.builder()
                    .user(applicantUser)
                    .nickname(applicantUser.getName())
                    .intro(foundApplication.getIntro())
                    .career(foundApplication.getCareer())
                    .category(foundApplication.getTitle())
                    .createdAt(LocalDateTime.now())
                    .build();

            instructorRepository.save(newInstructor);
        }

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

        if (foundApplication.getStatus() == ApplyStatus.APPROVED) {
            throw new IllegalArgumentException("이미 승인된 신청서는 반려할 수 없습니다.");
        }

        if (foundApplication.getStatus() == ApplyStatus.REJECTED) {
            throw new IllegalArgumentException("이미 반려된 신청서입니다.");
        }

        foundApplication.setStatus(ApplyStatus.REJECTED);
        foundApplication.setRejectReason(rejectReason);
        foundApplication.setReviewedAt(LocalDateTime.now());

        User applicantUser = foundApplication.getUser();
        applicantUser.setRole(Role.USER);
        userRepository.save(applicantUser);

        return instructorApplyRepository.save(foundApplication);
    }
}