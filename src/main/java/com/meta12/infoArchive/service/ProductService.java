package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.ProductStatus;
import com.meta12.infoArchive.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 전체 상품 조회
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 검토중 상품 조회
    public List<Product> getWaitingProducts() {
        return productRepository.findByStatus(ProductStatus.WAITING);
    }

    // 승인
    public void approveProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        product.setStatus(ProductStatus.OPEN);
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
        product.setRejectReason(rejectReason);

        productRepository.save(product);
    }

    // 비공개 처리
    public void closeProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        product.setStatus(ProductStatus.CLOSED);
        product.setReviewedAt(LocalDateTime.now());

        productRepository.save(product);
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }
}