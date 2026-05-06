package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.OrderRequestDto;
import com.meta12.infoArchive.dto.ProductDto;
import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public void orderProduct(
            OrderRequestDto orderRequestDto
    )
    {

        orderService.processEntireOrder(orderRequestDto);
    }
}
