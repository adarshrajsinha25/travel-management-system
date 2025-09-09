package com.EaseTravel.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Entity
public class Payment {
    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private String status;
    private LocalDateTime paymentDate;
    // Add more fields as needed

    public void setId(Long id) { this.id = id; }

    public void setAmount(Double amount) { this.amount = amount; }

    public void setStatus(String status) { this.status = status; }

    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
}
