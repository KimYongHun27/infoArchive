package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductNameContaining(String keyword);
}