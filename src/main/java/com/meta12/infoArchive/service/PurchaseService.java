package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.*;
import com.meta12.infoArchive.repository.PurchaseRepository;
import com.meta12.infoArchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseService {
//구독 시 테이블에 유저 추가
    private final PurchaseRepository purchaseRepository;

    public void addUser(User user)
    {
        Purchase purchase = new Purchase();

        purchase.setUser(user);
    }

    public Purchase getPurchase(Long id)
    {
        Optional<Purchase> optionalPurchase = purchaseRepository.findById(id);
        Purchase purchase = null;
        if (optionalPurchase.isPresent())
        {
            purchase = optionalPurchase.get();
        }

        return purchase;
    }

    public void save(Purchase purchase)
    {
        purchaseRepository.save(purchase);
    }
}
