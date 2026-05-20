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

        /*
         * 중복 신청 허용
         * - 이미 강사여도 신청 가능
         * - 이미 강사 신청 검토중이어도 신청 가능
         * - 기존 신청 내역이 있어도 새 신청으로 저장
         */

        InstructorApply newApplication = InstructorApply.builder()
                .user(foundUser)
                .title(requestDto.getTitle())
                .intro(requestDto.getIntro())
                .career(requestDto.getCareer())
                .portfolioUrl(requestDto.getPortfolioUrl())
                .status(ApplyStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        /*
         * 역할 처리
         * - 일반 회원이면 INSTRUCTOR_PENDING으로 변경
         * - 이미 강사면 그대로 INSTRUCTOR 유지
         * - 이미 검토중이면 그대로 유지
         */
        if (foundUser.getRole() == Role.USER) {
            foundUser.setRole(Role.INSTRUCTOR_PENDING);
            userRepository.save(foundUser);
        }

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

        /*
         * 강사 테이블 중복 생성 방지
         * 이미 instructor가 있으면 새로 만들지 않음
         */
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

        /*
         * 반려 처리
         * - 일반 신청자였으면 USER로 되돌림
         * - 이미 강사인 사람은 반려해도 INSTRUCTOR 유지
         */
        if (applicantUser.getRole() == Role.INSTRUCTOR_PENDING) {
            applicantUser.setRole(Role.USER);
            userRepository.save(applicantUser);
        }

        return instructorApplyRepository.save(foundApplication);
    }
}