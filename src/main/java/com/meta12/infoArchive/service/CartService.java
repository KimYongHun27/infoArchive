package com.meta12.infoArchive.service;

import com.meta12.infoArchive.controller.CartController;
import com.meta12.infoArchive.entity.Cart;
import com.meta12.infoArchive.entity.Course;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final UserService userService;
    private final CourseService courseService;

    public Cart insert(Cart cart)
    {

        boolean isAlreadyExist = cartRepository.existsByUserIdAndCourseId(cart.getUser().getId(), cart.getCourse().getId());
        if (isAlreadyExist) {
            throw new IllegalStateException("이미 장바구니에 담긴 강의입니다.");
        }
        return cartRepository.save(cart);
    }

    public List<Cart> getCartListByUserId(Long id)
    {
        return cartRepository.findByUserId(id);
    }
}
