package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.MemberInfoUpdateDto;
import com.meta12.infoArchive.dto.PasswordChangeDto;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
            PasswordChangeDto requestDto
    ) {
        try {
            userService.changeMyPassword(authentication, requestDto);
            return "redirect:/mypage?password=true";
        } catch (IllegalArgumentException e) {
            return "redirect:/mypage?passwordError=true";
        }
    }
}