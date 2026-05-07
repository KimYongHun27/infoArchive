package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.UserRequestDto;
import com.meta12.infoArchive.dto.UserSignupRequestDto;
import com.meta12.infoArchive.dto.MemberInfoUpdateDto;
import com.meta12.infoArchive.dto.PasswordChangeDto;
import com.meta12.infoArchive.entity.Role;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            @Lazy BCryptPasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User foundUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("이메일 또는 비밀번호가 틀렸습니다."));

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
                .createdAt(LocalDateTime.now())
                .build();

        return userRepository.save(newUser);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
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

    public void deleteUser(Long userId) {

        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        userRepository.delete(foundUser);
    }

    // 현재 로그인 회원 조회
    public User getLoginUser(Authentication authentication) {

        if (authentication == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("로그인 회원을 찾을 수 없습니다."));
    }

    // 회원정보 수정
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
    public void changeMyPassword(Authentication authentication, PasswordChangeDto requestDto) {

        User user = getLoginUser(authentication);

        if (!passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        if (!requestDto.getNewPassword().equals(requestDto.getNewPasswordCheck())) {
            throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다.");
        }

        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
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

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if (!Boolean.TRUE.equals(dto.getAgreeTerms())
                || !Boolean.TRUE.equals(dto.getAgreePrivacy())
                || !Boolean.TRUE.equals(dto.getAgreeAge())) {
            throw new IllegalArgumentException("필수 약관에 동의해야 합니다.");
        }

        User user = User.builder()
                .username(dto.getEmail())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .phone(dto.getPhone())
                .role(Role.USER)
                .emailAgree(Boolean.TRUE.equals(dto.getEmailAgree()))
                .smsAgree(Boolean.TRUE.equals(dto.getSmsAgree()))
                .pushAgree(Boolean.TRUE.equals(dto.getPushAgree()))
                .build();

        userRepository.save(user);
    }
}