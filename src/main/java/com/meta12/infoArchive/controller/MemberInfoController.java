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
public class MemberInfoController {

    private final UserService userService;

    // 마이페이지 기본 진입: /mypage
    @GetMapping("/mypage")
    public String mypageHome() {
        return "redirect:/dashboard";
    }

    // 회원 정보 화면: /member-info
    @GetMapping("/member-info")
    public String memberInfoPage(Authentication authentication, Model model) {
        User user = userService.getLoginUser(authentication);
        model.addAttribute("user", user);
        return "mypage/member-info";
    }

    // 회원 정보 수정
    @PostMapping("/member-info/update")
    public String updateMemberInfo(
            Authentication authentication,
            MemberInfoUpdateDto requestDto
    ) {
        userService.updateMyInfo(authentication, requestDto);
        return "redirect:/member-info?update=true";
    }

    // 비밀번호 변경
    @PostMapping("/member-info/password")
    public String changePassword(
            Authentication authentication,
            PasswordChangeDto requestDto,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            userService.changeMyPassword(authentication, requestDto);

            SecurityContextHolder.clearContext();

            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }

            Cookie cookie = new Cookie("JSESSIONID", null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            return "redirect:/login?passwordChanged=true";

        } catch (IllegalArgumentException e) {
            return "redirect:/member-info?passwordError=true";
        }
    }

    // 회원 탈퇴
    @PostMapping("/member-info/delete-account")
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