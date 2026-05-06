package com.meta12.infoArchive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PaymentController {

    @GetMapping("/payment/payment-complete")
    public String paymentComplate()
    {

        return "payment/payment-complete";
    }


    @GetMapping("/payment/checkout")
    public String view()
    {
        return "payment/checkout";
    }



}
