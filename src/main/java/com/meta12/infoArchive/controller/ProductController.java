package com.meta12.infoArchive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {

    @GetMapping("/product/{id}")
    public String productDetail(
            @PathVariable Long id,
            Model model
    ) {
        model.addAttribute("productId", id);
        return "product/detail";
    }
}