package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.Enrollment;
import com.meta12.infoArchive.entity.Payment;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.entity.WishList;
import com.meta12.infoArchive.repository.EnrollmentRepository;
import com.meta12.infoArchive.repository.PaymentRepository;
import com.meta12.infoArchive.repository.UserRepository;
import com.meta12.infoArchive.repository.WishListRepository;
import com.meta12.infoArchive.service.ReviewService;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final ReviewService reviewService;
    private final UserRepository userRepository;

    private final EnrollmentRepository enrollmentRepository;
    private final WishListRepository wishListRepository;
    private final PaymentRepository paymentRepository;

    private static final Set<String> ALLOWED_CATEGORIES = Set.of(
            "cooking",
            "craft",
            "design",
            "drawing",
            "finance",
            "fitness",
            "language",
            "music",
            "photo-video",
            "programming",
            "video-3d"
    );

    @GetMapping({"", "/"})
    public String index() {
        return "main";
    }

    @GetMapping("/main")
    public String list() {
        return "main";
    }

    @GetMapping("/faq")
    public String faq() {
        return "faq";
    }

    @GetMapping("/category/{categoryName}")
    public String categoryPage(
            @PathVariable String categoryName,
            Model model
    ) {
        if (!ALLOWED_CATEGORIES.contains(categoryName)) {
            return "redirect:/main";
        }

        model.addAttribute("categoryName", categoryName);
        model.addAttribute("courses", new ArrayList<>());

        return "category/" + categoryName;
    }

    @GetMapping("/dashboard")
    public String dashboardPage(Authentication authentication, Model model) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        User user = userService.getLoginUser(authentication);

        List<Enrollment> enrollmentList =
                enrollmentRepository.findByUserIdOrderByEnrolledAtDesc(user.getId());

        List<WishList> wishList =
                wishListRepository.findByUserIdOrderByIdDesc(user.getId());

        List<Payment> recentPayments =
                paymentRepository.findTop5ByUserOrderByOrderDateDesc(user);

        long takingCourseCount = enrollmentList.size();

        long completedCourseCount = enrollmentList.stream()
                .filter(enrollment -> Boolean.TRUE.equals(enrollment.getCompleted()))
                .count();

        long wishlistCount = wishList.size();

        model.addAttribute("user", user);

        model.addAttribute("takingCourseCount", takingCourseCount);
        model.addAttribute("completedCourseCount", completedCourseCount);
        model.addAttribute("wishlistCount", wishlistCount);

        model.addAttribute("enrollmentList", enrollmentList);
        model.addAttribute("wishList", wishList);
        model.addAttribute("recentPayments", recentPayments);

        return "mypage/dashboard";
    }
}