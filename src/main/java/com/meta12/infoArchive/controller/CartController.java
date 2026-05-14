package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.CartDto;
import com.meta12.infoArchive.entity.Cart;
import com.meta12.infoArchive.entity.Course;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.service.CartService;
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

    //전체보기
    @GetMapping("/cart")
    public String cartList(Model model)
    {
        List<Cart> cartList;
        model.addAttribute("cartList", 1);
        return "cart/cart";
    }

    //제품추가
//    @PostMapping("/cart/add")
//    public String cartToAdd(
//            CartDto cartDto
//    )
//       {
//           cartService.insert(cartDto.getUserId(), cartDto.getCourseId());
//           return "redirect:/cart/add";
//       }
}
