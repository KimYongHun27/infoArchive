package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.WishList;
import com.meta12.infoArchive.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WishListService {
    private final WishListRepository wishListRepository;

    public void insert(WishList wishList)
    {
        wishListRepository.save(wishList);
    }
}
