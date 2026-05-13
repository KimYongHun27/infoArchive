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
import com.meta12.infoArchive.entity.ApplyStatus;
import java.time.LocalDateTime;
import com.meta12.infoArchive.dto.AdminCreateRequestDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.meta12.infoArchive.repository.ProductRepository;
import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.ProductStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final BCryptPasswordEncoder passwordEncoder;
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

    // 관리자 - 회원 단건 조회
    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }

    // 관리자 - 강사 신청 단건 조회
    public InstructorApply getInstructorApplication(Long applyId) {
        return instructorApplyRepository.findById(applyId)
                .orElseThrow(() -> new IllegalArgumentException("강사 신청을 찾을 수 없습니다."));
    }

    // 관리자 - 강사 신청 승인
    public void approveInstructorApplication(Long applyId) {

        InstructorApply apply = instructorApplyRepository.findById(applyId)
                .orElseThrow(() -> new IllegalArgumentException("강사 신청을 찾을 수 없습니다."));

        if (apply.getStatus() == ApplyStatus.APPROVED) {
            throw new IllegalArgumentException("이미 승인된 신청입니다.");
        }

        User user = apply.getUser();

        // 회원 권한을 강사로 변경
        user.setRole(Role.INSTRUCTOR);

        // Instructor 테이블에 강사 정보 생성
        // 이미 강사로 등록되어 있으면 중복 생성 방지
        if (!instructorRepository.existsByUser(user)) {
            Instructor instructor = Instructor.builder()
                    .nickname(user.getName())
                    .intro(apply.getIntro())
                    .career(apply.getCareer())
                    .category(apply.getTitle())
                    .user(user)
                    .createdAt(LocalDateTime.now())
                    .build();

            instructorRepository.save(instructor);
        }

        // 신청 상태 승인 처리
        apply.setStatus(ApplyStatus.APPROVED);
        apply.setReviewedAt(LocalDateTime.now());
        apply.setRejectReason(null);

        userRepository.save(user);
        instructorApplyRepository.save(apply);
    }

    // 관리자 - 강사 신청 반려
    public void rejectInstructorApplication(Long applyId, String rejectReason) {

        InstructorApply apply = instructorApplyRepository.findById(applyId)
                .orElseThrow(() -> new IllegalArgumentException("강사 신청을 찾을 수 없습니다."));

        if (apply.getStatus() == ApplyStatus.APPROVED) {
            throw new IllegalArgumentException("이미 승인된 신청은 반려할 수 없습니다.");
        }

        apply.setStatus(ApplyStatus.REJECTED);
        apply.setRejectReason(rejectReason);
        apply.setReviewedAt(LocalDateTime.now());

        instructorApplyRepository.save(apply);
    }

    // 관리자 - 회원 삭제
    public void deleteUserByAdmin(Long userId) {

        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        userRepository.delete(foundUser);
    }

    // 기존 권한 변경 기능은 일단 남겨둬도 됨
    public User changeUserRole(Long userId, Role role) {

        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        foundUser.setRole(role);

        return userRepository.save(foundUser);
    }

    // 관리자 - 관리자 계정 생성
    public void createAdmin(AdminCreateRequestDto dto) {

        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("이메일을 입력해주세요.");
        }

        if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        User admin = User.builder()
                .username(dto.getEmail())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .phone(dto.getPhone())
                .role(Role.ADMIN)
                .build();

        userRepository.save(admin);
    }

    // 관리자 - 전체 강의/상품 조회
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    // 관리자 - 상품 단건 조회
    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    // 관리자 - 강의/상품 승인
    public void approveProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        product.setStatus(ProductStatus.OPEN);
        product.setReviewedAt(LocalDateTime.now());
        product.setRejectReason(null);

        productRepository.save(product);
    }

    // 관리자 - 강의/상품 반려
    public void rejectProduct(Long productId, String rejectReason) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        product.setStatus(ProductStatus.REJECTED);
        product.setReviewedAt(LocalDateTime.now());

        if (rejectReason == null || rejectReason.trim().isEmpty()) {
            product.setRejectReason("관리자 검토 결과 반려되었습니다.");
        } else {
            product.setRejectReason(rejectReason);
        }

        productRepository.save(product);
    }

    // 관리자 - 강의/상품 비공개 처리
    public void closeProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        product.setStatus(ProductStatus.CLOSED);
        product.setReviewedAt(LocalDateTime.now());

        productRepository.save(product);
    }
}