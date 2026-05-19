package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.WishList;
import com.meta12.infoArchive.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class WishListController {

    private final WishListService wishListService;

    // 찜한 강의 페이지
    @GetMapping("/wishlist")
    public String view(Authentication authentication, Model model) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        List<WishList> wishList = wishListService.findMyWishList(authentication);

        model.addAttribute("wishList", wishList);
        model.addAttribute("wishCount", wishList.size());

        return "cart/wishlist";
    }

    // 찜 추가/삭제 토글
    @PostMapping("/wishlist/toggle/{productId}")
    public String toggleWishList(
            @PathVariable Long productId,
            Authentication authentication,
            HttpServletRequest request
    ) {
        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        wishListService.toggle(productId, authentication);

        String referer = request.getHeader("Referer");

        if (referer != null && !referer.isBlank()) {
            return "redirect:" + referer;
        }

        return "redirect:/top10";
    }

    // 찜한 강의 페이지에서 삭제
    @PostMapping("/wishlist/delete/{productId}")
    public String deleteWishList(
            @PathVariable Long productId,
            Authentication authentication
    ) {
        wishListService.delete(productId, authentication);

        return "redirect:/wishlist";
    }
}