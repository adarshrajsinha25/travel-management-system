package com.EaseTravel.service.impl;

import com.EaseTravel.travel_management_system.model.entity.Payment;
import com.EaseTravel.travel_management_system.repository.PaymentRepository;
import com.EaseTravel.travel_management_system.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment updatePayment(Long id, Payment payment) {
        Optional<Payment> existing = paymentRepository.findById(id);
        if (existing.isPresent()) {
            Payment p = existing.get();
            p.setAmount(payment.getAmount());
            p.setStatus(payment.getStatus());
            p.setPaymentDate(payment.getPaymentDate());
            return paymentRepository.save(p);
        }
        return null;
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
