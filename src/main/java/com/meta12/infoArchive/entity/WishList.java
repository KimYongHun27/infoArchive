package com.meta12.infoArchive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter

@Table(name = "wishlist",
        uniqueConstraints = {@UniqueConstraint(name = "uk_user_lecture",
                columnNames = {"user_id", "lecture_id"})})
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //유저 fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //강의 fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    //최근 담은 순서 정렬용
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public static WishList createWishList(User user, Lecture lecture) {
        WishList wishList = new WishList();
        wishList.user = user;
        wishList.lecture = lecture;
        return wishList;
    }
}
