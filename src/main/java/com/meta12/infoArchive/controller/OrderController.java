package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.OrderRequestDto;
import com.meta12.infoArchive.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/membership")
    public String membershipList()
    {
        return "membership";
    }
    @PostMapping("")
    public String orderProduct(
            OrderRequestDto orderRequestDto
    )
    {
        orderService.processEntireOrder(orderRequestDto);

        return "";
    }
}
