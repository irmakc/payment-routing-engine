package com.irmakgenc.paymentrouting.domain.model;

import com.irmakgenc.paymentrouting.domain.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue
    private UUID id;

    private BigDecimal amount;

    private String currency;

    private String customerId;

    // Payment provider selected by the routing engine
    private String provider;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDateTime createdAt;

    @Column(unique = true)
    private String idempotencyKey;

    public Payment(BigDecimal amount, String currency, String customerId) {
        this.amount = amount;
        this.currency = currency;
        this.customerId = customerId;
        this.status = PaymentStatus.CREATED;
        this.createdAt = LocalDateTime.now();
    }
}