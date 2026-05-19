package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.Cart;
import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.CartRepository;
import com.meta12.infoArchive.repository.ProductRepository;
import com.meta12.infoArchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private User getLoginUser(Authentication authentication) {

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("로그인 사용자를 찾을 수 없습니다."));
    }

    // 장바구니 담기
    @Transactional
    public void insertCart(Long productId, Authentication authentication) {

        User user = getLoginUser(authentication);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        boolean alreadyExists =
                cartRepository.existsByUserIdAndProductId(user.getId(), product.getId());

        if (alreadyExists) {
            return;
        }

        Cart cart = Cart.createCart(user, product);

        cartRepository.save(cart);
    }

    // 내 장바구니 목록
    @Transactional(readOnly = true)
    public List<Cart> getMyCartList(Authentication authentication) {

        User user = getLoginUser(authentication);

        return cartRepository.findByUserIdOrderByIdDesc(user.getId());
    }

    // 장바구니 상품 1개 삭제
    @Transactional
    public void deleteCart(Long productId, Authentication authentication) {

        User user = getLoginUser(authentication);

        cartRepository.deleteByUserIdAndProductId(user.getId(), productId);
    }

    // 장바구니 전체 비우기
    @Transactional
    public void clearCart(Authentication authentication) {

        User user = getLoginUser(authentication);

        cartRepository.deleteByUserId(user.getId());
    }

    // 장바구니 개수
    @Transactional(readOnly = true)
    public int getCartCount(Authentication authentication) {

        User user = getLoginUser(authentication);

        return cartRepository.countByUserId(user.getId());
    }
}