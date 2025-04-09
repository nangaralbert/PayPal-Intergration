package com.albert.PayPal.Intergration.controller;


import com.albert.PayPal.Intergration.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:63342")
@RequestMapping("/api/v1/paypal")
public class PaypalController {
    @Autowired
    private PaypalService paypalService;

    private static final String SUCCESS_URL  = "http://localhost:8080/api/v1/paypal/success";
    private static final String CANCEL_URL   = "http://localhost:8080/api/v1/paypal/cancel";

    @PostMapping("/pay")
    public String makePayment(@RequestParam double amount){

        try {
            Payment    payment = paypalService.createPayment(
                    amount,
                    "USD",
                    "paypal",
                    "sale",
                    "payment description",
                    CANCEL_URL,
                    SUCCESS_URL);
            for(Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return "Redirect to: "+links.getHref();

                }
            }
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }

        return "Error processing the payment";

    }


    @GetMapping("/success")
    public String paymentSuccess(@RequestParam("paymentId") String paymentId , @RequestParam("PayerID") String payerId){


        try {
            Payment payment = paypalService.execute(paymentId, payerId);
            if(payment.getState().equals("approved")){
                return "payment is successfully done";
            }
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }


        return  "payment failed";
    }

}
