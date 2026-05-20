package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.AdminCreateRequestDto;
import com.meta12.infoArchive.dto.PasswordChangeDto;
import com.meta12.infoArchive.entity.InstructorApply;
import com.meta12.infoArchive.entity.Payment;
import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.service.AdminService;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminPageController {

    private final AdminService adminService;
    private final UserService userService;

    @GetMapping("/admin")
    public String adminPage(Model model) {

        List<User> users = adminService.getAllUsers();
        List<Product> products = adminService.getAllProducts();
        List<Payment> payments = adminService.getAllPayments();
        List<InstructorApply> applications = adminService.getAllInstructorApplications();

        model.addAttribute("users", users);

        model.addAttribute("userCount", users.size());
        model.addAttribute("productCount", products.size());
        model.addAttribute("paymentCount", payments.size());
        model.addAttribute("applyCount", applications.size());

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
    public String adminUserDetailPage(@PathVariable Long userId, Model model) {

        User user = adminService.getUser(userId);

        model.addAttribute("user", user);

        return "admin-user-detail";
    }

    // 회원 삭제 대신 비활성화
    @PostMapping("/admin/users/{userId}/delete")
    public String deleteUserFromPage(@PathVariable Long userId) {

        try {
            adminService.deleteUserByAdmin(userId);
            return "redirect:/admin/users?deleteSuccess=true";

        } catch (Exception e) {
            return "redirect:/admin/users?deleteError=true";
        }
    }

    // 회원 복구
    @PostMapping("/admin/users/{userId}/restore")
    public String restoreUserFromPage(@PathVariable Long userId) {

        try {
            adminService.restoreUserByAdmin(userId);
            return "redirect:/admin/users?restoreSuccess=true";

        } catch (Exception e) {
            return "redirect:/admin/users?restoreError=true";
        }
    }

    @GetMapping("/admin/instructor-applications")
    public String instructorApplicationsPage(Model model) {

        List<InstructorApply> applications = adminService.getAllInstructorApplications();

        model.addAttribute("applications", applications);
        model.addAttribute("applicationCount", applications.size());

        return "admin-instructor-applications";
    }

    @PostMapping("/admin/instructor-applications/{applyId}/approve")
    public String approveInstructorApplication(@PathVariable Long applyId) {

        adminService.approveInstructorApplication(applyId);

        return "redirect:/admin/instructor-applications";
    }

    @PostMapping("/admin/instructor-applications/{applyId}/reject")
    public String rejectInstructorApplication(
            @PathVariable Long applyId,
            @RequestParam(required = false) String rejectReason
    ) {

        adminService.rejectInstructorApplication(applyId, rejectReason);

        return "redirect:/admin/instructor-applications";
    }

    @GetMapping("/admin/create")
    public String adminCreatePage() {
        return "admin-create";
    }

    @PostMapping("/admin/create")
    public String createAdmin(AdminCreateRequestDto dto) {

        try {
            adminService.createAdmin(dto);
            return "redirect:/admin/users?createSuccess=true";

        } catch (IllegalArgumentException e) {
            return "redirect:/admin/create?error=true";
        }
    }

    @GetMapping("/admin/products")
    public String adminProductsPage(Model model) {

        List<Product> products = adminService.getAllProducts();

        model.addAttribute("products", products);
        model.addAttribute("productCount", products.size());

        return "admin-products";
    }

    @PostMapping("/admin/products/{productId}/approve")
    public String approveProduct(@PathVariable Long productId) {

        adminService.approveProduct(productId);

        return "redirect:/admin/products?approveSuccess=true";
    }

    @PostMapping("/admin/products/{productId}/reject")
    public String rejectProduct(
            @PathVariable Long productId,
            @RequestParam(required = false) String rejectReason
    ) {

        adminService.rejectProduct(productId, rejectReason);

        return "redirect:/admin/products?rejectSuccess=true";
    }

    @PostMapping("/admin/password/change")
    public String changeAdminPassword(
            Authentication authentication,
            PasswordChangeDto passwordChangeDto
    ) {

        try {
            userService.changeMyPassword(authentication, passwordChangeDto);
            return "redirect:/admin/my-info?passwordSuccess=true";

        } catch (IllegalArgumentException e) {
            return "redirect:/admin/my-info?passwordError=true";
        }
    }

    @GetMapping("/admin/payments")
    public String adminPaymentsPage(Model model) {

        List<Payment> payments = adminService.getAllPayments();

        model.addAttribute("payments", payments);
        model.addAttribute("paymentCount", payments.size());

        return "admin-payments";
    }

    @GetMapping("/admin/my-info")
    public String adminMyInfo(Authentication authentication, Model model) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        User user = userService.getLoginUser(authentication);

        model.addAttribute("user", user);

        return "admin-my-info";
    }
}