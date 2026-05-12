package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final ProductService productService;

    @GetMapping("/search")
    public String search(
            @RequestParam(value = "kw", required = false) String kw,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model
    ) {
        Page<Product> paging = productService.searchProducts(kw, PageRequest.of(page, 10));

        model.addAttribute("kw", kw);
        model.addAttribute("paging", paging);

        return "search";
    }
}