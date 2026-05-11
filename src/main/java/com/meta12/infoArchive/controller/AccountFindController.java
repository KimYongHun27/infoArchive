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

        String foundEmail = userService.findEmail(dto);

        model.addAttribute("foundEmail", foundEmail);
        model.addAttribute("activeTab", "id");

        return "account-find";
    }

    @PostMapping("/account/reset-password")
    public String resetPasswordProc(PasswordFindRequestDto dto, Model model) {

        userService.resetPassword(dto);

        model.addAttribute("passwordSuccess", true);
        model.addAttribute("activeTab", "password");

        return "account-find";
    }
}