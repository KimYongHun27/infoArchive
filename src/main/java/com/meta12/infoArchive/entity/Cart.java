package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "cart", uniqueConstraints = {@UniqueConstraint(name = "uk_user_cart_lecture", columnNames = {"user_id", "lecture_id"})})
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture; // 강의 엔티티

    // 장바구니에 담은 시점 (정렬용)
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // 생성 메서드
    public static Cart createCart(User user, Lecture lecture) {
        Cart cart = new Cart();
        cart.user = user;
        cart.lecture = lecture;
        return cart;
    }
}
