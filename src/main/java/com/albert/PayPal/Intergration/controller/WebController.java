package com.albert.PayPal.Intergration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/paypal")
public class WebController {
    @GetMapping("/")
    public String home (){
        return "payment";

    }

    @GetMapping("/cancel")
    public String cancel(){
        return "payment cancelled";
    }
}
