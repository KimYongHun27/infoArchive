package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.ProductDto;
import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.ProductStatus;
import com.meta12.infoArchive.entity.ProductType;
import com.meta12.infoArchive.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 단건 조회
    public Product getProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    // 전체 상품 조회
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 검토중 상품 조회
    public List<Product> getWaitingProducts() {
        return productRepository.findByStatus(ProductStatus.WAITING);
    }

    // 검색
    public Page<Product> searchProducts(String kw, Pageable pageable) {

        if (kw == null || kw.trim().isEmpty()) {
            return productRepository.findByStatus(ProductStatus.APPROVED, pageable);
        }

        return productRepository.findByProductNameContainingAndStatus(
                kw.trim(),
                ProductStatus.APPROVED,
                pageable
        );
    }
    // 강사 - 강의 등록
    public void createProduct(ProductDto dto) {

        Product product = new Product();

        product.setProductType(ProductType.COURSE);
        product.setProductName(dto.getProductName());
        product.setCategory(dto.getCategory());
        product.setInstructorName(dto.getInstructorName());

        // 썸네일 파일 저장
        String thumbnailUrl = saveThumbnailFile(dto.getThumbnailFile());
        product.setThumbnailUrl(thumbnailUrl);

        // 영상은 URL로 저장
        product.setVideoUrl(dto.getVideoUrl());

        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setDiscountRate(dto.getDiscountRate());

        // 강사가 등록하면 검토중
        product.setStatus(ProductStatus.WAITING);
        product.setCreatedAt(LocalDateTime.now());

        productRepository.save(product);
    }

    // 승인
    public void approveProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        product.setStatus(ProductStatus.APPROVED);
        product.setReviewedAt(LocalDateTime.now());
        product.setRejectReason(null);

        productRepository.save(product);
    }

    // 반려
    public void rejectProduct(Long productId, String rejectReason) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        product.setStatus(ProductStatus.REJECTED);
        product.setReviewedAt(LocalDateTime.now());

        if (rejectReason == null || rejectReason.trim().isEmpty()) {
            product.setRejectReason("관리자 검토 결과 반려되었습니다.");
        } else {
            product.setRejectReason(rejectReason);
        }

        productRepository.save(product);
    }

    private String saveThumbnailFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            String uploadDir = "uploads/thumbnails/";

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            String extension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String savedFileName = UUID.randomUUID() + extension;

            File saveFile = new File(uploadDir + savedFileName);
            file.transferTo(saveFile);

            return "/uploads/thumbnails/" + savedFileName;

        } catch (IOException e) {
            throw new RuntimeException("썸네일 이미지 저장 중 오류가 발생했습니다.", e);
        }
    }
}