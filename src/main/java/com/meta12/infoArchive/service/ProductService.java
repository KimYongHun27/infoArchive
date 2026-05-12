package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product getProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        return optionalProduct.orElse(null);
    }

    public Page<Product> searchProducts(String kw, Pageable pageable) {

        if (kw == null || kw.trim().isEmpty()) {
            return productRepository.findAll(pageable);
        }

        return productRepository.findByProductNameContaining(kw.trim(), pageable);
    }
}