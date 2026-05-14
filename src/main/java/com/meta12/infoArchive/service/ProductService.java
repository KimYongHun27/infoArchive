package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.Review;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.ProductRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<Product> getList(int page, String kw){
        Pageable pageable = PageRequest.of(page,10);
        Specification<Product> spec = search(kw);
        return productRepository.findAll(spec,pageable);
    }

    private Specification<Product> search(String kw) {
        return (Root<Product> p, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            query.distinct(true);

//            // 리뷰 작성자(User) 조인
//            Join<Review, User> u = r.join("user", JoinType.LEFT);
//            // 상품 조인
//            Join<Review, Product> p = r.join("product",JoinType.LEFT);

            return cb.or(
                    cb.like(p.get("productName"), "%" + kw + "%")
//                    cb.like(r.get("title"), "%" + kw + "%"),      // 리뷰 제목
//                    cb.like(r.get("content"), "%" + kw + "%"),    // 리뷰 내용
//                    cb.like(u.get("name"), "%" + kw + "%"),      // 유저 이름
//                    cb.like(p.get("productName"),"%" + kw + "%") // 상품명
            );
        };
    }
}