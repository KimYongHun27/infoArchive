package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // 로그인 유저의 장바구니 목록
    List<Cart> findByUserIdOrderByIdDesc(Long userId);

    // 이미 담긴 상품인지 확인
    boolean existsByUserIdAndProductId(Long userId, Long productId);

    // 특정 상품 삭제
    void deleteByUserIdAndProductId(Long userId, Long productId);

    // 내 장바구니 전체 비우기
    void deleteByUserId(Long userId);

    // 장바구니 개수
    int countByUserId(Long userId);
}