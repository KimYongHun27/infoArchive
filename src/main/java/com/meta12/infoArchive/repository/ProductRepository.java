package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByProductNameContaining(String kw, Pageable pageable);

    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
}