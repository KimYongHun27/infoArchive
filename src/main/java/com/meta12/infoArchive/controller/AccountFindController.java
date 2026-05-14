package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.IdFindRequestDto;
import com.meta12.infoArchive.dto.PasswordFindRequestDto;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AccountFindController {

    private final UserService userService;

    @GetMapping("/account/find")
    public String accountFindPage() {
        return "account-find";
    }

    @PostMapping("/account/find-id")
    public String findIdProc(IdFindRequestDto dto, Model model) {

        System.out.println("===== 이메일 찾기 진입 =====");
        System.out.println("name = " + dto.getName());
        System.out.println("phone = " + dto.getPhone());

        model.addAttribute("activeTab", "id");
        model.addAttribute("debugName", dto.getName());
        model.addAttribute("debugPhone", dto.getPhone());

        try {
            String foundEmail = userService.findEmail(dto);

            model.addAttribute("foundEmail", foundEmail);
            model.addAttribute("activeTab", "id");

        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("activeTab", "id");
        }
        return "account-find";
    }

    @PostMapping("/account/reset-password")
    public String resetPasswordProc(PasswordFindRequestDto dto, Model model) {

        model.addAttribute("activeTab", "password");

        try {
            String temporaryPassword = userService.issueTemporaryPassword(dto);

            model.addAttribute("passwordSuccess", true);
            model.addAttribute("temporaryPassword", temporaryPassword);

        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "account-find";
    }
}