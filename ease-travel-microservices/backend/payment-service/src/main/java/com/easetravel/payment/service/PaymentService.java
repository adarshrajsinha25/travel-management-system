package com.easetravel.payment.service;

import com.easetravel.payment.dto.request.PaymentRequest;
import com.easetravel.payment.dto.response.PaymentResponse;

import java.util.List;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest request);
    PaymentResponse getPaymentById(Long id);
    PaymentResponse getPaymentByBooking(Long bookingId);
    List<PaymentResponse> getPaymentsByUser(Long userId);
}

