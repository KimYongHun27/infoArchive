package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.PaymentConfirmRequestDto;
import com.meta12.infoArchive.entity.Cart;
import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.entity.UserCoupon;
import com.meta12.infoArchive.repository.CartRepository;
import com.meta12.infoArchive.service.CouponService;
import com.meta12.infoArchive.service.EnrollmentService;
import com.meta12.infoArchive.service.PaymentService;
import com.meta12.infoArchive.service.ProductService;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;
    private final ProductService productService;
    private final CouponService couponService;

    private final CartRepository cartRepository;
    private final EnrollmentService enrollmentService;

    // 멤버십 페이지
    @GetMapping("/membership")
    public String membershipPage(Authentication authentication, Model model) {

        // 비로그인 사용자는 멤버십 안내 페이지 볼 수 있음
        if (isAnonymous(authentication)) {
            model.addAttribute("login", false);
            model.addAttribute("membershipActive", false);

            return "membership";
        }

        // 관리자는 멤버십 페이지 접근 금지
        if (hasRole(authentication, "ROLE_ADMIN")) {
            return "redirect:/admin";
        }

        // 강사는 멤버십 페이지 접근 금지
        if (hasRole(authentication, "ROLE_INSTRUCTOR")) {
            return "redirect:/instructor";
        }

        // 특별계정도 멤버십 페이지 접근 금지
        if (hasRole(authentication, "ROLE_SPECIAL")) {
            return "redirect:/special";
        }

        // 일반회원만 멤버십 페이지 이용 가능
        if (!hasRole(authentication, "ROLE_USER")) {
            return "redirect:/main";
        }

        User user = userService.getLoginUser(authentication);

        model.addAttribute("login", true);
        model.addAttribute("user", user);
        model.addAttribute("membershipActive", userService.isMembershipActive(user));

        return "membership";
    }

    // 단일 상품 / 멤버십 결제 페이지
    @GetMapping("/payment/checkout")
    public String checkoutPage(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) String membershipType,
            Authentication authentication,
            Model model
    ) {
        if (isAnonymous(authentication)) {
            return "redirect:/login";
        }

        // 관리자 / 강사 / 특별계정은 결제 페이지 접근 차단
        if (hasRole(authentication, "ROLE_ADMIN")) {
            return "redirect:/admin";
        }

        if (hasRole(authentication, "ROLE_INSTRUCTOR")) {
            return "redirect:/instructor";
        }

        if (hasRole(authentication, "ROLE_SPECIAL")) {
            return "redirect:/special";
        }

        if (!hasRole(authentication, "ROLE_USER")) {
            return "redirect:/main";
        }

        User user = userService.getLoginUser(authentication);

        if (productId != null && userService.isMembershipActive(user)) {
            return "redirect:/product/" + productId;
        }

        String orderId = createOrderId();

        // 강의 단일 결제
        if (productId != null) {
            Product product = productService.getProduct(productId);

            if (product == null) {
                return "redirect:/top10";
            }

            List<UserCoupon> availableCoupons = couponService.getAvailableCoupons(authentication);

            model.addAttribute("availableCoupons", availableCoupons);
            model.addAttribute("originalAmount", product.getPrice());
            model.addAttribute("discountAmount", 0);
            model.addAttribute("orderId", orderId);
            model.addAttribute("amount", product.getPrice());
            model.addAttribute("product", product);
            model.addAttribute("productId", product.getId());
            model.addAttribute("productName", product.getProductName());
            model.addAttribute("membershipType", "COURSE");

            return "payment/checkout";
        }

        // 멤버십 결제
        if ("MONTHLY".equals(membershipType)) {

            if (userService.isMembershipActive(user)) {
                return "redirect:/membership?already=true";
            }

            model.addAttribute("orderId", orderId);
            model.addAttribute("amount", 9900);
            model.addAttribute("productId", null);
            model.addAttribute("productName", "INFO ARCHIVE 프리미엄 멤버십");
            model.addAttribute("membershipType", "MONTHLY");
            model.addAttribute("availableCoupons", List.of());
            model.addAttribute("originalAmount", 9900);
            model.addAttribute("discountAmount", 0);

            return "payment/checkout";
        }

        return "redirect:/top10";
    }

    // 장바구니 결제 페이지
    @PostMapping("/payment/checkout")
    public String checkoutCartPage(
            @RequestParam(value = "cartIds", required = false) List<Long> cartIds,
            Authentication authentication,
            Model model
    ) {
        if (isAnonymous(authentication)) {
            return "redirect:/login";
        }

        if (hasRole(authentication, "ROLE_ADMIN")) {
            return "redirect:/admin";
        }

        if (hasRole(authentication, "ROLE_INSTRUCTOR")) {
            return "redirect:/instructor";
        }

        if (hasRole(authentication, "ROLE_SPECIAL")) {
            return "redirect:/special";
        }

        if (!hasRole(authentication, "ROLE_USER")) {
            return "redirect:/main";
        }

        if (cartIds == null || cartIds.isEmpty()) {
            return "redirect:/cart";
        }

        User user = userService.getLoginUser(authentication);

        List<Cart> cartList = cartRepository.findAllById(cartIds);

        if (cartList.isEmpty()) {
            return "redirect:/cart";
        }

        for (Cart cart : cartList) {
            if (!cart.getUser().getId().equals(user.getId())) {
                return "redirect:/cart";
            }
        }

        int totalPrice = cartList.stream()
                .mapToInt(cart -> cart.getProduct().getPrice() * cart.getQuantity())
                .sum();

        String orderId = createOrderId();

        List<UserCoupon> availableCoupons = couponService.getAvailableCoupons(authentication);

        model.addAttribute("availableCoupons", availableCoupons);
        model.addAttribute("originalAmount", totalPrice);
        model.addAttribute("discountAmount", 0);
        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", totalPrice);
        model.addAttribute("productId", null);
        model.addAttribute("productName", "장바구니 강의 결제");
        model.addAttribute("membershipType", "CART");
        model.addAttribute("cartIds", cartIds);
        model.addAttribute("cartList", cartList);

        return "payment/checkout";
    }

    // 결제 처리
    @PostMapping("/payment/confirm")
    public String confirmPayment(
            PaymentConfirmRequestDto dto,
            Authentication authentication
    ) {
        if (isAnonymous(authentication)) {
            return "redirect:/login";
        }

        if (hasRole(authentication, "ROLE_ADMIN")) {
            return "redirect:/admin";
        }

        if (hasRole(authentication, "ROLE_INSTRUCTOR")) {
            return "redirect:/instructor";
        }

        if (hasRole(authentication, "ROLE_SPECIAL")) {
            return "redirect:/special";
        }

        if (!hasRole(authentication, "ROLE_USER")) {
            return "redirect:/main";
        }

        try {
            paymentService.confirmPayment(dto, authentication);

            User user = userService.getLoginUser(authentication);

            if ("MONTHLY".equals(dto.getMembershipType())) {
                userService.activateMembership(authentication);
            }

            if ("CART".equals(dto.getMembershipType())) {
                enrollmentService.enrollProducts(user, dto.getCartIds());
            }

            if ("COURSE".equals(dto.getMembershipType())) {
                enrollmentService.enrollProduct(user, dto.getProductId());
            }

            return "redirect:/payment/payment-complete";

        } catch (IllegalArgumentException e) {
            return "redirect:/membership?already=true";
        }
    }

    @GetMapping("/payment/payment-complete")
    public String paymentCompletePage(Authentication authentication) {

        if (isAnonymous(authentication)) {
            return "redirect:/login";
        }

        if (hasRole(authentication, "ROLE_ADMIN")) {
            return "redirect:/admin";
        }

        if (hasRole(authentication, "ROLE_INSTRUCTOR")) {
            return "redirect:/instructor";
        }

        if (hasRole(authentication, "ROLE_SPECIAL")) {
            return "redirect:/special";
        }

        return "payment/payment-complete";
    }

    @PostMapping("/membership/cancel")
    public String cancelMembership(Authentication authentication) {

        if (isAnonymous(authentication)) {
            return "redirect:/login";
        }

        if (hasRole(authentication, "ROLE_ADMIN")) {
            return "redirect:/admin";
        }

        if (hasRole(authentication, "ROLE_INSTRUCTOR")) {
            return "redirect:/instructor";
        }

        if (hasRole(authentication, "ROLE_SPECIAL")) {
            return "redirect:/special";
        }

        if (!hasRole(authentication, "ROLE_USER")) {
            return "redirect:/main";
        }

        try {
            userService.cancelMembership(authentication);
            return "redirect:/membership?cancel=true";

        } catch (IllegalArgumentException e) {
            return "redirect:/membership?error=true";
        }
    }

    private String createOrderId() {
        return "ORDER-" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    private boolean isAnonymous(Authentication authentication) {
        return authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal());
    }

    private boolean hasRole(Authentication authentication, String role) {
        if (authentication == null) {
            return false;
        }

        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
    }
}