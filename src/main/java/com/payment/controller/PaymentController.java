package com.payment.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.payload.PaymentRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final String secretKey;

    public PaymentController(@Value("${stripe.secretKey}") String secretKey) {
        this.secretKey = secretKey;
        Stripe.apiKey = secretKey;
    }

    
    @PostMapping("/create-payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentRequest paymentRequest) {
        try {
            PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                    .setAmount(paymentRequest.getAmount() * 100) // Convert amount to cents
                    .setCurrency(paymentRequest.getCurrency())
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(createParams);

            return ResponseEntity.ok("PaymentIntent created successfully with ID: " + paymentIntent.getId());
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating PaymentIntent: " + e.getMessage());
        }
    }
}
