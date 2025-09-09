package com.EaseTravel.service;

import com.EaseTravel.model.entity.Payment;
import java.util.List;

public interface PaymentService {
    Payment createPayment(Payment payment);

    Payment getPaymentById(Long id);

    List<Payment> getAllPayments();

    Payment updatePayment(Long id, Payment payment);

    void deletePayment(Long id);
}
