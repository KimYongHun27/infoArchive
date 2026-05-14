package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.service.ProductService;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

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

        if (Boolean.TRUE.equals(product.getPremiumOnly())) {

            if (authentication == null
                    || !authentication.isAuthenticated()
                    || "anonymousUser".equals(authentication.getPrincipal())) {
                return "redirect:/login";
            }

            User user = userService.getLoginUser(authentication);

            if (!userService.isMembershipActive(user)) {
                return "redirect:/membership?required=true";
            }
        }

        model.addAttribute("product", product);
        return "product/detail";
    }
}