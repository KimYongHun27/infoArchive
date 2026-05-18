package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.CartDto;
import com.meta12.infoArchive.entity.Cart;
import com.meta12.infoArchive.entity.Course;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.service.CartService;
import com.meta12.infoArchive.service.CourseService;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;

@RequiredArgsConstructor
@Controller
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final CourseService courseService;

    //전체보기
    @GetMapping("/cart")
    public String cartList(Model model, Long currentUserId) {
        List<Cart> cartList = cartService.getCartListByUserId(currentUserId);
        model.addAttribute("cartList", cartList);
        return "cart/cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(CartDto dto) {

        User user = userService.getUser(dto.getUserId());
        Course course = courseService.getCourse(dto.getCourseId()); // DTO에 courseId가 있다고 가정

        Cart cart = Cart.createCart(user, course, 1);
        cartService.insert(cart);

        return "redirect:/cart";
    }
}

