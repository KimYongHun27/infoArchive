package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.ProductStatus;
import com.meta12.infoArchive.entity.ProductType;
import com.meta12.infoArchive.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstructorCourseService {

    private final ProductRepository productRepository;

    public void createCourse(Authentication authentication, InstructorCourseCreateDto dto) {

        Product product = new Product();

        product.setProductName(dto.getTitle());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setDiscountRate(dto.getDiscountRate());
        product.setThumbnailUrl(dto.getThumbnailUrl());
        product.setDescription(dto.getDescription());

        product.setInstructorName(authentication.getName());

        product.setProductType(ProductType.COURSE);
        product.setStatus(ProductStatus.WAITING);

        productRepository.save(product);
    }

    // 강의 수정
    public void updateCourse(Long id, InstructorCourseCreateDto dto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의가 없습니다. id=" + id));

        product.setProductName(dto.getTitle());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setDiscountRate(dto.getDiscountRate());
        product.setThumbnailUrl(dto.getThumbnailUrl());
        product.setDescription(dto.getDescription());

        productRepository.save(product);
    }
}