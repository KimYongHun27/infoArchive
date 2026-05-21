package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.Enrollment;
import com.meta12.infoArchive.entity.PaymentStatus;
import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.Role;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.EnrollmentRepository;
import com.meta12.infoArchive.repository.PaymentRepository;
import com.meta12.infoArchive.service.EnrollmentService;
import com.meta12.infoArchive.service.ProductService;
import com.meta12.infoArchive.service.ReviewService;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final ReviewService reviewService;
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentService enrollmentService;
    private final PaymentRepository paymentRepository;

    // 상품 상세 페이지
    @GetMapping("/product/{id}")
    public String productDetail(
            @PathVariable Long id,
            Authentication authentication,
            Model model
    ) {
        Product product = productService.getProduct(id);

        if (product == null) {
            return "redirect:/";
        }

        boolean login = false;
        boolean membershipActive = false;
        boolean enrolled = false;
        boolean paid = false;
        boolean canWatch = false;
        int progressRate = 0;
        String userRole = "GUEST";

        if (authentication != null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {

            login = true;

            User user = userService.getLoginUser(authentication);
            userRole = user.getRole().name();

            if (user.getRole() == Role.USER) {
                membershipActive = userService.isMembershipActive(user);

                enrolled = enrollmentService.isEnrolled(user, id);

                paid = paymentRepository.existsByUserAndProductIdAndPaymentStatus(
                        user,
                        id,
                        PaymentStatus.COMPLETED
                );

                canWatch = membershipActive || enrolled || paid;

                progressRate = enrollmentRepository
                        .findByUserIdAndProductId(user.getId(), id)
                        .map(enrollment -> enrollment.getProgressRate() != null ? enrollment.getProgressRate() : 0)
                        .orElse(0);
            }

            // 관리자와 강사는 상세 확인용으로 영상 접근 허용
            if (user.getRole() == Role.ADMIN || user.getRole() == Role.INSTRUCTOR) {
                canWatch = true;
            }
        }

        model.addAttribute("product", product);
        model.addAttribute("productId", id);
        model.addAttribute("reviews", reviewService.findByProduct(product));

        model.addAttribute("login", login);
        model.addAttribute("userRole", userRole);
        model.addAttribute("membershipActive", membershipActive);
        model.addAttribute("enrolled", enrolled);
        model.addAttribute("paid", paid);
        model.addAttribute("canWatch", canWatch);
        model.addAttribute("progressRate", progressRate);

        return "category/detail";
    }

    // 수강중인 강의 목록
    @GetMapping("/taking-course")
    public String takingCourse(Authentication authentication, Model model) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        User user = userService.getLoginUser(authentication);

        if (user.getRole() == Role.ADMIN) {
            return "redirect:/admin";
        }

        if (user.getRole() == Role.INSTRUCTOR) {
            return "redirect:/instructor";
        }

        List<Enrollment> enrollmentList =
                enrollmentRepository.findByUserIdOrderByEnrolledAtDesc(user.getId());

        model.addAttribute("enrollmentList", enrollmentList);

        return "mypage/taking-course";
    }

    // 이어서 학습하기 / 강의보기 버튼 클릭 처리
    @GetMapping("/course/start/{productId}")
    public String startCourse(
            @PathVariable Long productId,
            Authentication authentication
    ) {
        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        User user = userService.getLoginUser(authentication);

        if (user.getRole() == Role.ADMIN) {
            return "redirect:/admin/products";
        }

        if (user.getRole() == Role.INSTRUCTOR) {
            return "redirect:/instructor/courses";
        }

        boolean membershipActive = userService.isMembershipActive(user);

        boolean alreadyEnrolled = enrollmentService.isEnrolled(user, productId);

        boolean paid = paymentRepository.existsByUserAndProductIdAndPaymentStatus(
                user,
                productId,
                PaymentStatus.COMPLETED
        );

        // 결제도 안 했고, 멤버십도 아니고, 수강 등록도 안 되어 있으면 결제 페이지로 이동
        if (!membershipActive && !paid && !alreadyEnrolled) {
            return "redirect:/payment/checkout?productId=" + productId;
        }

        // 결제 완료했거나 멤버십인데 수강 등록이 아직 없으면 수강 등록 생성
        if ((membershipActive || paid) && !alreadyEnrolled) {
            enrollmentService.enrollProduct(user, productId);
        }

        // 수강 등록 후 상세페이지로 이동
        return "redirect:/product/" + productId;
    }

    // 학습 진도율 저장
    @PostMapping("/product/{productId}/progress")
    @ResponseBody
    public String updateProductProgress(
            @PathVariable Long productId,
            @RequestParam Integer watchedSeconds,
            @RequestParam Integer totalSeconds,
            Authentication authentication
    ) {
        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "login-required";
        }

        User user = userService.getLoginUser(authentication);

        if (user.getRole() == Role.ADMIN || user.getRole() == Role.INSTRUCTOR) {
            return "not-user";
        }

        boolean membershipActive = userService.isMembershipActive(user);

        boolean enrolled = enrollmentService.isEnrolled(user, productId);

        boolean paid = paymentRepository.existsByUserAndProductIdAndPaymentStatus(
                user,
                productId,
                PaymentStatus.COMPLETED
        );

        // 수강 권한 없으면 진도율 저장 차단
        if (!membershipActive && !enrolled && !paid) {
            return "no-permission";
        }

        enrollmentService.updateProgress(authentication, productId, watchedSeconds, totalSeconds);

        return "ok";
    }
}