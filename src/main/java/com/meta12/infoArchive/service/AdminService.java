package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.Instructor;
import com.meta12.infoArchive.entity.InstructorApply;
import com.meta12.infoArchive.entity.Role;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.InstructorApplyRepository;
import com.meta12.infoArchive.repository.InstructorRepository;
import com.meta12.infoArchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;
    private final InstructorApplyRepository instructorApplyRepository;

    // 관리자 - 전체 회원 조회
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 관리자 - 전체 강사 조회
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    // 관리자 - 전체 강사 신청 조회
    public List<InstructorApply> getAllInstructorApplications() {
        return instructorApplyRepository.findAll();
    }

    // 관리자 - 회원 권한 변경
    public User changeUserRole(Long userId, Role role) {

        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        foundUser.setRole(role);

        return userRepository.save(foundUser);
    }

    // 관리자 - 회원 삭제
    public void deleteUserByAdmin(Long userId) {

        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        userRepository.delete(foundUser);
    }
}