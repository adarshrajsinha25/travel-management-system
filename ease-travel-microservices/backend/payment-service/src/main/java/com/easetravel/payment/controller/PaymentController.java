package com.easetravel.payment.controller;

import com.easetravel.payment.dto.request.PaymentRequest;
import com.easetravel.payment.dto.response.ApiResponse;
import com.easetravel.payment.dto.response.PaymentResponse;
import com.easetravel.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Payment processing endpoints")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "Process a payment for a booking")
    public ResponseEntity<ApiResponse<PaymentResponse>> processPayment(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<PaymentResponse>builder().success(true)
                        .message("Payment processed successfully")
                        .data(paymentService.processPayment(request)).build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment by ID")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<PaymentResponse>builder().success(true)
                .message("Payment retrieved").data(paymentService.getPaymentById(id)).build());
    }

    @GetMapping("/booking/{bookingId}")
    @Operation(summary = "Get payment by booking ID")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(ApiResponse.<PaymentResponse>builder().success(true)
                .message("Payment retrieved").data(paymentService.getPaymentByBooking(bookingId)).build());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all payments by user ID")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPaymentsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.<List<PaymentResponse>>builder().success(true)
                .message("User payments retrieved").data(paymentService.getPaymentsByUser(userId)).build());
    }
}

