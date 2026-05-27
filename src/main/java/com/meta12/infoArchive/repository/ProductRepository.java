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

    Page<Product> findByProductNameContaining(String kw, Pageable pageable);

    List<Product> findByStatus(ProductStatus status);

    Page<Product> findByStatus(ProductStatus status, Pageable pageable);

    List<Product> findTop10ByStatusOrderByCreatedAtDesc(ProductStatus status);

    Page<Product> findByProductNameContainingAndStatus(
            String kw,
            ProductStatus status,
            Pageable pageable
    );

    List<Product> findByCategoryAndStatus(String category, ProductStatus status);

    List<Product> findByCategoryAndStatusOrderByIdAsc(String category, ProductStatus status);

    List<Product> findByProductType(ProductType productType);

    List<Product> findByInstructorName(String instructorName);

    List<Product> findByInstructorNameAndProductType(
            String instructorName,
            ProductType productType
    );

    List<Product> findByInstructorNameAndStatusNotOrderByCreatedAtDesc(
            String instructorName,
            ProductStatus status
    );

    // 관리자 강의관리: 삭제되지 않은 강의 최신순
    List<Product> findByStatusNotOrderByCreatedAtDesc(ProductStatus status);

    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
}