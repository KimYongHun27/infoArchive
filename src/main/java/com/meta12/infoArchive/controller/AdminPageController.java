package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.InstructorApply;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.meta12.infoArchive.entity.Instructor;
import com.meta12.infoArchive.dto.AdminCreateRequestDto;
import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.dto.PasswordChangeDto;
import org.springframework.security.core.Authentication;
import com.meta12.infoArchive.service.UserService;


import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminPageController {

    private final AdminService adminService;
    private final UserService userService;

    @GetMapping("/admin")
    public String adminPage(Model model) {

        List<User> users = adminService.getAllUsers();

        model.addAttribute("users", users);
        model.addAttribute("userCount", users.size());

        // 아직 강의/결제는 연결 안 되어 있으면 0으로
        model.addAttribute("userCount", users.size());
        model.addAttribute("productCount", 0);
        model.addAttribute("paymentCount", 0);
        model.addAttribute("applyCount", adminService.getAllInstructorApplications().size());

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
    public String approveInstructorApplication(@PathVariable Long applyId) {

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

    // 관리자 계정 생성 페이지
    @GetMapping("/admin/create")
    public String adminCreatePage() {
        return "admin-create";
    }

    // 관리자 계정 생성 처리
    @PostMapping("/admin/create")
    public String createAdmin(AdminCreateRequestDto dto) {
        adminService.createAdmin(dto);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/products")
    public String adminProductsPage(Model model) {

        List<Product> products = adminService.getAllProducts();

        model.addAttribute("products", products);
        model.addAttribute("productCount", products.size());

        return "admin-products";
    }

    @PostMapping("/admin/password/change")
    public String changeAdminPassword(
            Authentication authentication,
            PasswordChangeDto passwordChangeDto
    ) {
        userService.changeMyPassword(authentication, passwordChangeDto);
        return "redirect:/admin?passwordSuccess=true";
    }
}