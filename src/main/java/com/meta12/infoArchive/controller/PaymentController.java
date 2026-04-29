package com.meta12.infoArchive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PaymentController {

    @GetMapping("/payment-complete")
    public String paymentComplate()
    {
        return "payment-complete";
    }


//    @GetMapping("/payment-complete")
//    public String view()
//    {
//        return "payment-complete";
//    }
//
//    @GetMapping("/payment-complete")
//    public String insert()
//    {
//        return "payment-complete";
//    }
//
//    @GetMapping("/payment-complete")
//    public String update()
//    {
//        return "payment-complete";
//    }
//



}
