package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.ProductStatus;
import com.meta12.infoArchive.entity.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 상품명 검색
    Page<Product> findByProductNameContaining(String kw, Pageable pageable);

    // 상태별 조회 - List용
    List<Product> findByStatus(ProductStatus status);

    // 상태별 조회 - Page용
    Page<Product> findByStatus(ProductStatus status, Pageable pageable);

    // 사용자 화면: 승인된 상품만 검색
    Page<Product> findByProductNameContainingAndStatus(
            String kw,
            ProductStatus status,
            Pageable pageable
    );

    // 카테고리 + 승인 상태 조회
    List<Product> findByCategoryAndStatus(String category, ProductStatus status);

    // 강의 타입별 조회
    List<Product> findByProductType(ProductType productType);

    // 강사 이메일/이름 기준 조회
    List<Product> findByInstructorName(String instructorName);

    // 강사 + 타입 기준 조회
    List<Product> findByInstructorNameAndProductType(
            String instructorName,
            ProductType productType
    );
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
}