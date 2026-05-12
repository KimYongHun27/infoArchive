package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product getProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product = null;

        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
        }

        return product;
    }

    // 상품명 검색
    public List<Product> searchProducts(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return List.of();
        }

        return productRepository.findByProductNameContaining(keyword.trim());
    }
}