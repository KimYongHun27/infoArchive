package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.ProductStatus;
import com.meta12.infoArchive.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final ProductRepository productRepository;

    @GetMapping("/category/design")
    public String design(Model model) {

        List<Product> products =
                productRepository.findByCategoryAndStatus("design", ProductStatus.APPROVED);

        model.addAttribute("products", products);
        model.addAttribute("categoryName", "디자인");

        return "category/design";
    }

    @GetMapping("/category/drawing")
    public String drawing(Model model) {

        List<Product> products =
                productRepository.findByCategoryAndStatus("drawing", ProductStatus.APPROVED);

        model.addAttribute("products", products);
        model.addAttribute("categoryName", "드로잉");

        return "category/drawing";
    }

    @GetMapping("/category/programming")
    public String programming(Model model) {

        List<Product> products =
                productRepository.findByCategoryAndStatus("programming", ProductStatus.APPROVED);

        model.addAttribute("products", products);
        model.addAttribute("categoryName", "프로그래밍");

        return "category/programming";
    }

    @GetMapping("/category/craft")
    public String craft(Model model) {

        List<Product> products =
                productRepository.findByCategoryAndStatus("craft", ProductStatus.APPROVED);

        model.addAttribute("products", products);
        model.addAttribute("categoryName", "공예");

        return "category/craft";
    }

    @GetMapping("/category/finance")
    public String finance(Model model) {

        List<Product> products =
                productRepository.findByCategoryAndStatus("finance", ProductStatus.APPROVED);

        model.addAttribute("products", products);
        model.addAttribute("categoryName", "금융 · 재테크");

        return "category/finance";
    }

    @GetMapping("/category/photo-video")
    public String photoVideo(Model model) {

        List<Product> products =
                productRepository.findByCategoryAndStatus("photo-video", ProductStatus.APPROVED);

        model.addAttribute("products", products);
        model.addAttribute("categoryName", "사진 · 영상");

        return "category/photo-video";
    }

    @GetMapping("/category/fitness")
    public String fitness(Model model) {

        List<Product> products =
                productRepository.findByCategoryAndStatus("fitness", ProductStatus.APPROVED);

        model.addAttribute("products", products);
        model.addAttribute("categoryName", "운동");

        return "category/fitness";
    }

    @GetMapping("/category/music")
    public String music(Model model) {

        List<Product> products =
                productRepository.findByCategoryAndStatus("music", ProductStatus.APPROVED);

        model.addAttribute("products", products);
        model.addAttribute("categoryName", "음악");

        return "category/music";
    }

    @GetMapping("/category/video-3d")
    public String video3d(Model model) {

        List<Product> products =
                productRepository.findByCategoryAndStatus("video-3d", ProductStatus.APPROVED);

        model.addAttribute("products", products);
        model.addAttribute("categoryName", "영상/3D");

        return "category/video-3d";
    }

    @GetMapping("/category/cooking")
    public String cooking(Model model) {

        List<Product> products =
                productRepository.findByCategoryAndStatus("cooking", ProductStatus.APPROVED);

        model.addAttribute("products", products);
        model.addAttribute("categoryName", "베이킹 · 요리");

        return "category/cooking";
    }

    @GetMapping("/category/language")
    public String language(Model model) {

        List<Product> products =
                productRepository.findByCategoryAndStatus("language", ProductStatus.APPROVED);

        model.addAttribute("products", products);
        model.addAttribute("categoryName", "제2 외국어");

        return "category/language";
    }
}