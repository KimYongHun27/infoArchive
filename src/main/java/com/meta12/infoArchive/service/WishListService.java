package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.entity.WishList;
import com.meta12.infoArchive.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final UserService userService;
    private final ProductService productService;

    public List<WishList> findMyWishList(Authentication authentication) {
        User user = userService.getLoginUser(authentication);
        return wishListRepository.findByUserOrderByCreatedAtDesc(user);
    }

    @Transactional
    public void toggle(Long productId, Authentication authentication) {
        User user = userService.getLoginUser(authentication);
        Product product = productService.getProduct(productId);

        if (product == null) {
            throw new IllegalArgumentException("상품을 찾을 수 없습니다.");
        }

        if (wishListRepository.existsByUserAndProduct(user, product)) {
            wishListRepository.deleteByUserAndProduct(user, product);
            return;
        }

        WishList wishList = WishList.createWishList(user, product);
        wishListRepository.save(wishList);
    }

    @Transactional
    public void delete(Long productId, Authentication authentication) {
        User user = userService.getLoginUser(authentication);
        Product product = productService.getProduct(productId);

        if (product == null) {
            throw new IllegalArgumentException("상품을 찾을 수 없습니다.");
        }

        wishListRepository.deleteByUserAndProduct(user, product);
    }

    public long countMyWishList(Authentication authentication) {
        User user = userService.getLoginUser(authentication);
        return wishListRepository.countByUser(user);
    }

    public List<Long> findMyWishProductIds(Authentication authentication) {
        User user = userService.getLoginUser(authentication);

        return wishListRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(wish -> wish.getProduct().getId())
                .toList();
    }
}