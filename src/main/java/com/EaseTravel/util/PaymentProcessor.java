package com.EaseTravel.util;

public class PaymentProcessor {
    public static boolean processPayment(double amount, String method) {
        // Simulate payment processing
        return amount > 0 && method != null && !method.isEmpty();
    }
}
