package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.Cart;
import com.meta12.infoArchive.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartService cartService;

    // 장바구니 목록
    @GetMapping("/cart")
    public String cartList(Authentication authentication, Model model) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        List<Cart> cartList = cartService.getMyCartList(authentication);

        int totalPrice = cartList.stream()
                .mapToInt(cart -> cart.getProduct().getPrice() * cart.getQuantity())
                .sum();

        model.addAttribute("cartList", cartList);
        model.addAttribute("totalPrice", totalPrice);

        return "cart/cart";
    }

    // 장바구니 담기
    @PostMapping("/cart/insert/{productId}")
    public String insertCart(@PathVariable("productId") Long productId,
                             Authentication authentication,
                             @RequestHeader(value = "Referer", required = false) String referer) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        cartService.insertCart(productId, authentication);

        if (referer != null && !referer.isBlank()) {
            return "redirect:" + referer;
        }

        return "redirect:/cart";
    }

    // 장바구니 상품 삭제
    @PostMapping("/cart/delete/{productId}")
    public String deleteCart(@PathVariable("productId") Long productId,
                             Authentication authentication) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        cartService.deleteCart(productId, authentication);

        return "redirect:/cart";
    }

    // 장바구니 전체 비우기
    @PostMapping("/cart/clear")
    public String clearCart(Authentication authentication) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        cartService.clearCart(authentication);

        return "redirect:/cart";
    }
}