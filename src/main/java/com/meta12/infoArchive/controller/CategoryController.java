package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.ProductStatus;
import com.meta12.infoArchive.repository.ProductRepository;
import com.meta12.infoArchive.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final ProductRepository productRepository;
    private final WishListService wishListService;

    private List<Long> getLikedProductIds(Authentication authentication) {

        if (authentication != null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {
            return wishListService.findMyWishProductIds(authentication);
        }

        return List.of();
    }

    private void addCategoryModel(
            Model model,
            Authentication authentication,
            String category,
            String categoryName
    ) {
        List<Product> products =
                productRepository.findByCategoryAndStatusOrderByIdAsc(
                        category,
                        ProductStatus.APPROVED
                );

        List<Long> likedProductIds = getLikedProductIds(authentication);

        model.addAttribute("products", products);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("likedProductIds", likedProductIds);
    }

    @GetMapping("/top10")
    public String top10(Authentication authentication, Model model) {

        List<Long> likedProductIds = getLikedProductIds(authentication);

        List<Product> products =
                productRepository.findTop10ByStatusOrderByCreatedAtDesc(ProductStatus.APPROVED);

        model.addAttribute("products", products);
        model.addAttribute("likedProductIds", likedProductIds);

        return "top10";
    }

    @GetMapping("/category/drawing")
    public String drawing(Model model, Authentication authentication) {
        addCategoryModel(model, authentication, "drawing", "드로잉");
        return "category/drawing";
    }

    @GetMapping("/category/craft")
    public String craft(Model model, Authentication authentication) {
        addCategoryModel(model, authentication, "craft", "공예");
        return "category/craft";
    }

    @GetMapping("/category/finance")
    public String finance(Model model, Authentication authentication) {
        addCategoryModel(model, authentication, "finance", "금융·재테크");
        return "category/finance";
    }

    @GetMapping("/category/design")
    public String design(Model model, Authentication authentication) {
        addCategoryModel(model, authentication, "design", "디자인");
        return "category/design";
    }

    @GetMapping("/category/photo-video")
    public String photoVideo(Model model, Authentication authentication) {
        addCategoryModel(model, authentication, "photo-video", "사진·영상");
        return "category/photo-video";
    }

    @GetMapping("/category/fitness")
    public String fitness(Model model, Authentication authentication) {
        addCategoryModel(model, authentication, "fitness", "운동");
        return "category/fitness";
    }

    @GetMapping("/category/music")
    public String music(Model model, Authentication authentication) {
        addCategoryModel(model, authentication, "music", "음악");
        return "category/music";
    }

    @GetMapping("/category/video-3d")
    public String video3d(Model model, Authentication authentication) {
        addCategoryModel(model, authentication, "video-3d", "영상/3D");
        return "category/video-3d";
    }

    @GetMapping("/category/cooking")
    public String cooking(Model model, Authentication authentication) {
        addCategoryModel(model, authentication, "cooking", "베이킹·요리");
        return "category/cooking";
    }

    @GetMapping("/category/language")
    public String language(Model model, Authentication authentication) {
        addCategoryModel(model, authentication, "language", "제2외국어");
        return "category/language";
    }

    @GetMapping("/category/programming")
    public String programming(Model model, Authentication authentication) {
        addCategoryModel(model, authentication, "programming", "프로그래밍");
        return "category/programming";
    }
}