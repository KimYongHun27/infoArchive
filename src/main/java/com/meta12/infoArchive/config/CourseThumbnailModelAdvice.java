package com.meta12.infoArchive.config;

import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class CourseThumbnailModelAdvice {

    private final ProductRepository productRepository;

    @ModelAttribute("thumbnailMap")
    public Map<Long, String> thumbnailMap() {

        List<Product> productList = productRepository.findAll();

        Map<Long, String> thumbnailMap = new HashMap<>();

        for (Product product : productList) {
            if (product.getThumbnailUrl() != null && !product.getThumbnailUrl().isEmpty()) {
                thumbnailMap.put(product.getId(), product.getThumbnailUrl());
            }
        }

        return thumbnailMap;
    }
}