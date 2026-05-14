package com.meta12.infoArchive.service;

import com.meta12.infoArchive.controller.CartController;
import com.meta12.infoArchive.entity.Cart;
import com.meta12.infoArchive.entity.Course;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserService userService;
    private final CourseService courseService;

    public Cart insert(Long userId, Long courseId)
    {
        User user = userService.getUser(userId);
        Course course = courseService.getCourse(courseId);
        return cartRepository.save(Cart.createCart(user, course));
    }

    public List<Cart> getCartList()
    {
        return cartRepository.findAll();
    }
}
