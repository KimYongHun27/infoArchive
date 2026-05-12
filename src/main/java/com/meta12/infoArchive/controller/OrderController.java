package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.OrderRequestDto;
import com.meta12.infoArchive.dto.PurchaseDto;
import com.meta12.infoArchive.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/orderList")
            public String orderList()
    {
        return "cartList";
    }

    @PostMapping("/orderInsert")
    public void orderInsert(
            OrderRequestDto orderRequestDto,
            Model model
    )
    {
        PurchaseDto purchaseDto = orderService.processEntireOrder(orderRequestDto);
        model.addAttribute("purchaseDto", purchaseDto);
    }
}
