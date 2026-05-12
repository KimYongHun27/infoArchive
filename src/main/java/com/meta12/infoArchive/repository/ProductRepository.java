package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByProductNameContaining(String kw, Pageable pageable);
}