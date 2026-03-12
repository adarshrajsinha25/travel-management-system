package com.easetravel.payment.service.impl;

import com.easetravel.payment.client.BookingServiceClient;
import com.easetravel.payment.dto.request.PaymentRequest;
import com.easetravel.payment.dto.response.ApiResponse;
import com.easetravel.payment.dto.response.BookingResponse;
import com.easetravel.payment.dto.response.PaymentResponse;
import com.easetravel.payment.entity.Payment;
import com.easetravel.payment.enums.PaymentStatus;
import com.easetravel.payment.exception.PaymentFailedException;
import com.easetravel.payment.exception.ResourceNotFoundException;
import com.easetravel.payment.mapper.PaymentMapper;
import com.easetravel.payment.repository.PaymentRepository;
import com.easetravel.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingServiceClient bookingServiceClient;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        // Validate booking
        ApiResponse<BookingResponse> bookingApiResp = bookingServiceClient.getBookingById(request.getBookingId());
        if (bookingApiResp == null || !bookingApiResp.isSuccess() || bookingApiResp.getData() == null) {
            throw new ResourceNotFoundException("Booking not found with id: " + request.getBookingId());
        }
        BookingResponse booking = bookingApiResp.getData();
        if ("CANCELLED".equals(booking.getStatus())) {
            throw new PaymentFailedException("Cannot process payment for a cancelled booking");
        }
        if ("CONFIRMED".equals(booking.getStatus())) {
            throw new PaymentFailedException("Booking is already paid and confirmed");
        }

        // Create payment record
        Payment payment = Payment.builder()
                .bookingId(request.getBookingId())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .method(request.getMethod())
                .status(PaymentStatus.INITIATED)
                .transactionId(UUID.randomUUID().toString())
                .build();
        payment = paymentRepository.save(payment);

        // Simulate payment processing (always SUCCESS for demo)
        try {
            log.info("Processing payment for booking {}, amount {}", request.getBookingId(), request.getAmount());
            payment.setStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);

            // Confirm the booking on success
            bookingServiceClient.confirmBooking(request.getBookingId());
            log.info("Payment SUCCESS. TransactionId={}", payment.getTransactionId());

        } catch (Exception e) {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            log.error("Payment FAILED for booking {}: {}", request.getBookingId(), e.getMessage());
            throw new PaymentFailedException("Payment processing failed: " + e.getMessage());
        }

        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(Long id) {
        return paymentMapper.toResponse(paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByBooking(Long bookingId) {
        return paymentMapper.toResponse(paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for booking id: " + bookingId)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByUser(Long userId) {
        return paymentRepository.findByUserId(userId).stream()
                .map(paymentMapper::toResponse).collect(Collectors.toList());
    }
}

