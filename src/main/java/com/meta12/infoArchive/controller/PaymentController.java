package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.PaymentConfirmRequestDto;
import com.meta12.infoArchive.entity.Cart;
import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.CartRepository;
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
import com.meta12.infoArchive.service.CouponService;
import com.meta12.infoArchive.entity.UserCoupon;

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

    // 단일 상품 / 멤버십 결제 페이지
    @GetMapping("/payment/checkout")
    public String checkoutPage(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) String membershipType,
            Authentication authentication,
            Model model
    ) {
        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
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
            model.addAttribute("amount", 239000);
            model.addAttribute("productId", null);
            model.addAttribute("productName", "INFO ARCHIVE 프리미엄 멤버십");
            model.addAttribute("membershipType", "MONTHLY");
            model.addAttribute("availableCoupons", List.of());
            model.addAttribute("originalAmount", 239000);
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
        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
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
        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
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
    public String paymentCompletePage() {
        return "payment/payment-complete";
    }

    private String createOrderId() {
        return "ORDER-" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}