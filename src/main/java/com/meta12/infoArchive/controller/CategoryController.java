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

    /*
     * 공통 찜 목록 조회
     */
    private List<Long> getLikedProductIds(Authentication authentication) {

        if (authentication != null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {
            return wishListService.findMyWishProductIds(authentication);
        }

        return List.of();
    }

    /*
     * 카테고리 공통 모델 세팅
     */
    private void addCategoryModel(
            Model model,
            Authentication authentication,
            String category,
            String categoryName
    ) {
        List<Product> products =
                productRepository.findByCategoryAndStatus(category, ProductStatus.APPROVED);

        List<Long> likedProductIds = getLikedProductIds(authentication);

        model.addAttribute("products", products);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("likedProductIds", likedProductIds);
    }

    /*
     * TOP10
     * TOP10은 10번대 상품을 HTML에서 직접 쓰고 있으므로
     * likedProductIds만 넘겨도 하트 active 처리가 가능함
     */
    @GetMapping("/top10")
    public String top10(Authentication authentication, Model model) {

        List<Long> likedProductIds = getLikedProductIds(authentication);

        model.addAttribute("likedProductIds", likedProductIds);

        return "top10";
    }

    /*
     * 드로잉
     * 상품 ID 기준: 101 ~ 110
     */
    @GetMapping("/category/drawing")
    public String drawing(Model model, Authentication authentication) {

        addCategoryModel(model, authentication, "drawing", "드로잉");

        return "category/drawing";
    }

    /*
     * 공예
     * 상품 ID 기준: 201 ~ 210
     */
    @GetMapping("/category/craft")
    public String craft(Model model, Authentication authentication) {

        addCategoryModel(model, authentication, "craft", "공예");

        return "category/craft";
    }

    /*
     * 금융·재테크
     * 상품 ID 기준: 301 ~ 310
     */
    @GetMapping("/category/finance")
    public String finance(Model model, Authentication authentication) {

        addCategoryModel(model, authentication, "finance", "금융·재테크");

        return "category/finance";
    }

    /*
     * 디자인
     * 상품 ID 기준: 401 ~ 410
     */
    @GetMapping("/category/design")
    public String design(Model model, Authentication authentication) {

        addCategoryModel(model, authentication, "design", "디자인");

        return "category/design";
    }

    /*
     * 사진·영상
     * 상품 ID 기준: 501 ~ 510
     */
    @GetMapping("/category/photo-video")
    public String photoVideo(Model model, Authentication authentication) {

        addCategoryModel(model, authentication, "photo-video", "사진·영상");

        return "category/photo-video";
    }

    /*
     * 운동
     * 상품 ID 기준: 601 ~ 610
     */
    @GetMapping("/category/fitness")
    public String fitness(Model model, Authentication authentication) {

        addCategoryModel(model, authentication, "fitness", "운동");

        return "category/fitness";
    }

    /*
     * 음악
     * 상품 ID 기준: 701 ~ 710
     */
    @GetMapping("/category/music")
    public String music(Model model, Authentication authentication) {

        addCategoryModel(model, authentication, "music", "음악");

        return "category/music";
    }

    /*
     * 영상/3D
     * 상품 ID 기준: 801 ~ 810
     */
    @GetMapping("/category/video-3d")
    public String video3d(Model model, Authentication authentication) {

        addCategoryModel(model, authentication, "video-3d", "영상/3D");

        return "category/video-3d";
    }

    /*
     * 베이킹·요리
     * 상품 ID 기준: 901 ~ 910
     */
    @GetMapping("/category/cooking")
    public String cooking(Model model, Authentication authentication) {

        addCategoryModel(model, authentication, "cooking", "베이킹·요리");

        return "category/cooking";
    }

    /*
     * 제2외국어
     * 상품 ID 기준: 1001 ~ 1010
     */
    @GetMapping("/category/language")
    public String language(Model model, Authentication authentication) {

        addCategoryModel(model, authentication, "language", "제2외국어");

        return "category/language";
    }

    /*
     * 프로그래밍
     * 상품 ID 기준: 1101 ~ 1110
     */
    @GetMapping("/category/programming")
    public String programming(Model model, Authentication authentication) {

        addCategoryModel(model, authentication, "programming", "프로그래밍");

        return "category/programming";
    }
}