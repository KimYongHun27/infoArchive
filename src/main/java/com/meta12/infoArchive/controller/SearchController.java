package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.Review;
import com.meta12.infoArchive.service.ProductService;
import com.meta12.infoArchive.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final ProductService productService;
//    private final SearchService searchService;

    @GetMapping("/search")
    public String search(

            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "kw", required = false) String kw,
            Model model
    ) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<Product> paging = productService.searchProducts(kw, pageable);
        //Page<Product> paging = productService.getList(page,kw);

        model.addAttribute("kw", kw);
        model.addAttribute("paging", paging);

        return "search";
    }
}