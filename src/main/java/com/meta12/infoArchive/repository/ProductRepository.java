package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.ProductStatus;
import com.meta12.infoArchive.entity.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByProductNameContaining(String kw, Pageable pageable);

    // 관리자: 상태별 강의/상품 조회
    List<Product> findByStatus(ProductStatus status);

    // 사용자 화면: 공개된 상품만 검색
    Page<Product> findByProductNameContainingAndStatus(
            String kw,
            ProductStatus status,
            Pageable pageable
    );

    // 추가 1: 강의 타입별 조회
    List<Product> findByProductType(ProductType productType);

    // 추가 2: 강사 이메일/이름 기준 조회
    List<Product> findByInstructorName(String instructorName);

    // 추가 3: 강사 + 타입 기준 조회
    List<Product> findByInstructorNameAndProductType(String instructorName, ProductType productType);
}