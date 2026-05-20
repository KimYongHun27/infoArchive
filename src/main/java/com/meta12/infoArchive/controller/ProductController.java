package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.Enrollment;
import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.EnrollmentRepository;
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

        if (authentication != null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {

            login = true;

            User user = userService.getLoginUser(authentication);
            membershipActive = userService.isMembershipActive(user);
            enrolled = enrollmentService.isEnrolled(user, id);
        }

        model.addAttribute("product", product);
        model.addAttribute("productId", id);
        model.addAttribute("reviews", reviewService.findByProduct(product));

        model.addAttribute("login", login);
        model.addAttribute("membershipActive", membershipActive);
        model.addAttribute("enrolled", enrolled);

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

        List<Enrollment> enrollmentList =
                enrollmentRepository.findByUserIdOrderByEnrolledAtDesc(user.getId());

        model.addAttribute("enrollmentList", enrollmentList);

        return "mypage/taking-course";
    }

    // 강의보기 버튼 클릭 처리
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

        boolean membershipActive = userService.isMembershipActive(user);
        boolean alreadyEnrolled = enrollmentService.isEnrolled(user, productId);

        // 멤버십도 아니고 구매도 안 한 일반 유저는 결제 페이지로 이동
        if (!membershipActive && !alreadyEnrolled) {
            return "redirect:/payment/checkout?productId=" + productId;
        }

        // 멤버십 유저는 결제 없이 수강 등록
        if (membershipActive && !alreadyEnrolled) {
            enrollmentService.enrollProduct(user, productId);
        }

        // 이미 구매한 유저 또는 멤버십 유저는 수강중인 강의로 이동
        return "redirect:/taking-course";
    }

    @PostMapping("/product/{productId}/progress")
    @ResponseBody
    public String updateProductProgress(
            @PathVariable Long productId,
            @RequestParam Integer watchedSeconds,
            @RequestParam Integer totalSeconds,
            Authentication authentication
    ) {
        enrollmentService.updateProgress(authentication, productId, watchedSeconds, totalSeconds);

        return "ok";
    }
}