package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "cart",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_cart_product",
                        columnNames = {"user_id", "product_id"}
                )
        }
)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 장바구니 주인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 장바구니에 담긴 상품
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 강의 상품은 기본 1개
    @Column(nullable = false)
    private int quantity = 1;

    // 담은 시점
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    public static Cart createCart(User user, Product product) {
        Cart cart = new Cart();
        cart.user = user;
        cart.product = product;
        cart.quantity = 1;
        return cart;
    }
}