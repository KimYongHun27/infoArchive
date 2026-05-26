package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.*;
import com.meta12.infoArchive.entity.Role;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.UserRepository;
import com.meta12.infoArchive.repository.ReviewRepository;
import com.meta12.infoArchive.repository.InstructorApplyRepository;
import com.meta12.infoArchive.repository.InstructorRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ReviewRepository reviewRepository;
    private final InstructorApplyRepository instructorApplyRepository;
    private final InstructorRepository instructorRepository;

    public UserService(
            UserRepository userRepository,
            @Lazy BCryptPasswordEncoder passwordEncoder,
            ReviewRepository reviewRepository,
            InstructorApplyRepository instructorApplyRepository,
            InstructorRepository instructorRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.reviewRepository = reviewRepository;
        this.instructorApplyRepository = instructorApplyRepository;
        this.instructorRepository = instructorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User foundUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("이메일 또는 비밀번호가 틀렸습니다."));

        if (Boolean.TRUE.equals(foundUser.getDeleted())
                || Boolean.FALSE.equals(foundUser.getEnabled())) {
            throw new UsernameNotFoundException("비활성화된 계정입니다.");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(foundUser.getEmail())
                .password(foundUser.getPassword())
                .roles(foundUser.getRole().name())
                .build();
    }

    public User createUser(UserRequestDto requestDto) {

        User newUser = User.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .phone(requestDto.getPhone())
                .role(Role.USER)
                .emailAgree(requestDto.getEmailAgree())
                .smsAgree(requestDto.getSmsAgree())
                .pushAgree(requestDto.getPushAgree())
                .enabled(true)
                .deleted(false)
                .membershipActive(false)
                .createdAt(LocalDateTime.now())
                .build();

        return userRepository.save(newUser);
    }

    public List<User> getUsers() {
        return userRepository.findByDeletedFalse();
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }

    public User updateUser(Long userId, UserRequestDto requestDto) {

        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        foundUser.setName(requestDto.getName());
        foundUser.setEmail(requestDto.getEmail());
        foundUser.setPhone(requestDto.getPhone());
        foundUser.setEmailAgree(requestDto.getEmailAgree());
        foundUser.setSmsAgree(requestDto.getSmsAgree());
        foundUser.setPushAgree(requestDto.getPushAgree());

        return userRepository.save(foundUser);
    }

    // 관리자나 내부용 삭제도 실제 삭제하지 않고 비활성화 처리
    @Transactional
    public void deleteUser(Long userId) {

        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        foundUser.setDeleted(true);
        foundUser.setEnabled(false);

        userRepository.save(foundUser);
    }

    // 현재 로그인 회원 조회
    public User getLoginUser(Authentication authentication) {

        if (authentication == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("로그인 회원을 찾을 수 없습니다."));

        if (Boolean.TRUE.equals(user.getDeleted())
                || Boolean.FALSE.equals(user.getEnabled())) {
            throw new IllegalArgumentException("비활성화된 계정입니다.");
        }

        return user;
    }

    // 회원정보 수정
    @Transactional
    public void updateMyInfo(Authentication authentication, MemberInfoUpdateDto requestDto) {

        User user = getLoginUser(authentication);

        user.setName(requestDto.getName());
        user.setPhone(requestDto.getPhone());

        user.setEmailAgree(Boolean.TRUE.equals(requestDto.getEmailAgree()));
        user.setSmsAgree(Boolean.TRUE.equals(requestDto.getSmsAgree()));
        user.setPushAgree(Boolean.TRUE.equals(requestDto.getPushAgree()));

        userRepository.save(user);
    }

    // 비밀번호 변경
    @Transactional
    public void changeMyPassword(Authentication authentication, PasswordChangeDto dto) {

        User user = getLoginUser(authentication);

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다.");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        userRepository.save(user);
    }

    // 휴대폰번호 변경
    @Transactional
    public void changeMyPhone(Authentication authentication, String phone) {

        User user = getLoginUser(authentication);

        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("휴대폰번호를 입력해주세요.");
        }

        String cleanedPhone = phone.trim();

        if (!cleanedPhone.matches("^01[0-9]-?[0-9]{3,4}-?[0-9]{4}$")) {
            throw new IllegalArgumentException("휴대폰번호 형식이 올바르지 않습니다.");
        }

        user.setPhone(cleanedPhone);

        userRepository.save(user);
    }

    public void signup(UserSignupRequestDto dto) {

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("이름을 입력해주세요.");
        }

        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("이메일을 입력해주세요.");
        }

        if (dto.getPhone() == null || dto.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("휴대폰 번호를 입력해주세요.");
        }

        if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }

        if (!dto.getPassword().equals(dto.getPasswordCheck())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String email = dto.getEmail().trim();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        if (!Boolean.TRUE.equals(dto.getAgreeTerms())
                || !Boolean.TRUE.equals(dto.getAgreePrivacy())
                || !Boolean.TRUE.equals(dto.getAgreeAge())) {
            throw new IllegalArgumentException("필수 약관에 동의해야 합니다.");
        }

        User user = User.builder()
                .username(email)
                .email(email)
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .phone(dto.getPhone())
                .role(Role.USER)
                .emailAgree(Boolean.TRUE.equals(dto.getEmailAgree()))
                .smsAgree(Boolean.TRUE.equals(dto.getSmsAgree()))
                .pushAgree(Boolean.TRUE.equals(dto.getPushAgree()))
                .enabled(true)
                .deleted(false)
                .membershipActive(false)
                .build();

        userRepository.save(user);
    }

    // 이메일 찾기
    public String findEmail(IdFindRequestDto dto) {

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("이름을 입력해주세요.");
        }

        if (dto.getPhone() == null || dto.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("전화번호를 입력해주세요.");
        }

        String inputName = dto.getName().trim();
        String inputPhone = dto.getPhone().replaceAll("[^0-9]", "");

        User user = userRepository.findAll().stream()
                .filter(u -> !Boolean.TRUE.equals(u.getDeleted()))
                .filter(u -> u.getName() != null && u.getName().equals(inputName))
                .filter(u -> u.getPhone() != null)
                .filter(u -> u.getPhone().replaceAll("[^0-9]", "").equals(inputPhone))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원 정보를 찾을 수 없습니다."));

        return user.getEmail();
    }

    // 임시 비밀번호 발급
    public String issueTemporaryPassword(PasswordFindRequestDto dto) {

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("이름을 입력해주세요.");
        }

        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("이메일을 입력해주세요.");
        }

        if (dto.getPhone() == null || dto.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("전화번호를 입력해주세요.");
        }

        String inputEmail = dto.getEmail().trim();
        String inputName = dto.getName().trim();
        String inputPhone = dto.getPhone().replaceAll("[^0-9]", "");

        User user = userRepository.findAll().stream()
                .filter(u -> !Boolean.TRUE.equals(u.getDeleted()))
                .filter(u -> u.getEmail() != null && u.getEmail().equals(inputEmail))
                .filter(u -> u.getName() != null && u.getName().equals(inputName))
                .filter(u -> u.getPhone() != null)
                .filter(u -> u.getPhone().replaceAll("[^0-9]", "").equals(inputPhone))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원 정보를 찾을 수 없습니다."));

        String temporaryPassword = createTemporaryPassword();

        user.setPassword(passwordEncoder.encode(temporaryPassword));
        userRepository.save(user);

        return temporaryPassword;
    }

    private String createTemporaryPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * chars.length());
            password.append(chars.charAt(index));
        }

        return password.toString();
    }

    // 구독권 가입 처리
    @Transactional
    public void activateMembership(Authentication authentication) {

        User user = getLoginUser(authentication);

        user.setMembershipActive(true);
        user.setMembershipStartedAt(LocalDateTime.now());
        user.setMembershipExpiredAt(LocalDateTime.now().plusMonths(1));

        userRepository.save(user);
    }

    // 구독권 유효 여부 확인
    public boolean isMembershipActive(User user) {

        if (user.getMembershipActive() == null || !user.getMembershipActive()) {
            return false;
        }

        if (user.getMembershipExpiredAt() == null) {
            return false;
        }

        return user.getMembershipExpiredAt().isAfter(LocalDateTime.now());
    }

    // 회원 탈퇴
    @Transactional
    public void withdrawMyAccount(Authentication authentication) {

        User user = getLoginUser(authentication);

        Long userId = user.getId();

        // 실제 삭제 X
        // enrollment, payment, review, answer, coupon 등 FK 연결 때문에 비활성화 처리
        user.setDeleted(true);
        user.setEnabled(false);

        // 개인정보 마스킹
        user.setName("탈퇴회원");
        user.setPhone(null);

        // unique 제약조건 때문에 이메일/아이디는 고유한 값으로 변경
        user.setEmail("deleted_user_" + userId + "@deleted.local");
        user.setUsername("deleted_user_" + userId);

        // 멤버십 종료
        user.setMembershipActive(false);
        user.setMembershipExpiredAt(LocalDateTime.now());

        userRepository.save(user);
    }

    @Transactional
    public void cancelMembership(Authentication authentication) {

        User user = getLoginUser(authentication);

        if (!isMembershipActive(user)) {
            throw new IllegalArgumentException("이용 중인 멤버십이 없습니다.");
        }

        user.setMembershipActive(false);
        user.setMembershipExpiredAt(LocalDateTime.now());

        userRepository.save(user);
    }
}