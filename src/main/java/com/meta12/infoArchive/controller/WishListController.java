package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.WishListDto;
import com.meta12.infoArchive.entity.Course;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.entity.WishList;
import com.meta12.infoArchive.service.CourseService;
import com.meta12.infoArchive.service.UserService;
import com.meta12.infoArchive.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class WishListController {
    private final WishListService wishListService;
    private final UserService userService;

    //전체보기
    @GetMapping("/wishlist")
    public String view()
    {
        return "cart/wishlist";
    }

    //상세보기

    //위시리스트 추가
    @PostMapping("wishlist/add")
    public String addToWishList(
        WishListDto dto
    )
    {
        User user = userService.getUser(dto.getUserId());
        Course course = new Course();
        wishListService.insert(WishList.createWishList(user, course));
        return "redirect:cart/wishlist";
    }
}
