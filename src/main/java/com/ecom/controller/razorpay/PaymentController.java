package com.ecom.controller.razorpay;

import com.ecom.service.razorpay.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-link")
    @CrossOrigin
    public ResponseEntity<String> createPaymentLink(@RequestParam String name, @RequestParam String email, @RequestParam double amount) {
        try {
            String paymentLink = paymentService.createPaymentLink(name, email, amount);
            return ResponseEntity.ok(paymentLink);
        } catch (RazorpayException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error generating payment link");
        }
    }
}
