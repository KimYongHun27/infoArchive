package com.meta12.infoArchive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentController {

    @GetMapping("/payment-complete")
    public String list()
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
//    @GetMapping("/payment-complete")
//    public String delete()
//    {
//        return "payment-complete";
//    }


}
