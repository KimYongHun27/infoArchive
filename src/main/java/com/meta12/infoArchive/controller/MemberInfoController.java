package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.MemberInfoUpdateDto;
import com.meta12.infoArchive.dto.PasswordChangeDto;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MemberInfoController {

    private final UserService userService;

    // 마이페이지 화면: /mypage
    @GetMapping
    public String memberInfoPage(Authentication authentication, Model model) {
        User user = userService.getLoginUser(authentication);
        model.addAttribute("user", user);
        return "mypage/member-info";
    }

    // 회원 정보 수정
    @PostMapping("/update")
    public String updateMemberInfo(
            Authentication authentication,
            MemberInfoUpdateDto requestDto
    ) {
        userService.updateMyInfo(authentication, requestDto);
        return "redirect:/mypage?update=true";
    }

    // 비밀번호 변경
    @PostMapping("/password")
    public String changePassword(
            Authentication authentication,
            PasswordChangeDto requestDto,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            userService.changeMyPassword(authentication, requestDto);

            // Spring Security 인증 정보 제거
            SecurityContextHolder.clearContext();

            // 세션 제거
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }

            // JSESSIONID 쿠키 제거
            Cookie cookie = new Cookie("JSESSIONID", null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            return "redirect:/login?passwordChanged=true";

        } catch (IllegalArgumentException e) {
            return "redirect:/mypage?passwordError=true";
        }
    }

    @PostMapping("/delete-account")
    public String deleteAccount(
            Authentication authentication,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        System.out.println("===== 회원 탈퇴 요청 들어옴 =====");

        userService.withdrawMyAccount(authentication);

        SecurityContextHolder.clearContext();

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/?deleteAccount=true";
    }
}