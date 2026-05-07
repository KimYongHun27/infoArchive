package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.InstructorApply;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminPageController {

    private final AdminService adminService;

    @GetMapping("/admin")
    public String adminPage(Model model) {

        List<User> users = adminService.getAllUsers();
        List<InstructorApply> applications = adminService.getAllInstructorApplications();

        model.addAttribute("users", users);
        model.addAttribute("userCount", users.size());
        model.addAttribute("applicationCount", applications.size());

        return "admin";
    }

    @GetMapping("/admin/users")
    public String adminUsersPage(Model model) {

        List<User> users = adminService.getAllUsers();

        model.addAttribute("users", users);
        model.addAttribute("userCount", users.size());

        return "admin-users";
    }

    @GetMapping("/admin/users/{userId}")
    public String adminUserDetailPage(
            @PathVariable Long userId,
            Model model
    ) {
        User user = adminService.getUser(userId);

        model.addAttribute("user", user);

        return "admin-user-detail";
    }


    @PostMapping("/admin/users/{userId}/delete")
    public String deleteUserFromPage(
            @PathVariable Long userId
    ) {
        adminService.deleteUserByAdmin(userId);
        return "redirect:/admin/users";
    }

    // 강사 신청 관리 페이지
    @GetMapping("/admin/instructor-applications")
    public String instructorApplicationsPage(Model model) {

        List<InstructorApply> applications = adminService.getAllInstructorApplications();

        model.addAttribute("applications", applications);
        model.addAttribute("applicationCount", applications.size());

        return "admin-instructor-applications";
    }

    // 강사 신청 승인
    @PostMapping("/admin/instructor-applications/{applyId}/approve")
    public String approveInstructorApplication(
            @PathVariable Long applyId
    ) {
        adminService.approveInstructorApplication(applyId);
        return "redirect:/admin/instructor-applications";
    }

    // 강사 신청 반려
    @PostMapping("/admin/instructor-applications/{applyId}/reject")
    public String rejectInstructorApplication(
            @PathVariable Long applyId,
            @RequestParam(required = false) String rejectReason
    ) {
        adminService.rejectInstructorApplication(applyId, rejectReason);
        return "redirect:/admin/instructor-applications";
    }
}