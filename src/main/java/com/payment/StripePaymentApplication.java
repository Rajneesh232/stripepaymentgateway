package com.payment;

import com.payment.controller.PaymentController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StripePaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(StripePaymentApplication.class, args);
	}

	@Value("${stripe.secretKey}")
	private String secretKey;

	@Bean
	public PaymentController paymentController() {
		return new PaymentController(secretKey);
	}

}

