package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.*;
import com.meta12.infoArchive.repository.ProductRepository;
import com.meta12.infoArchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product getProduct(Long id)
    {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product = null;

        if (optionalProduct.isPresent())
        {
            product = optionalProduct.get();
        }

        return product;
    }
}
