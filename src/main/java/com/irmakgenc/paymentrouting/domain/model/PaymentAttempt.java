package com.irmakgenc.paymentrouting.domain.model;

import com.irmakgenc.paymentrouting.domain.enums.AttemptStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payment_attempts")
public class PaymentAttempt {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID paymentId;

    @Column(nullable = false, length = 50)
    private String provider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AttemptStatus status;

    @Column(length = 500)
    private String failureReason;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public PaymentAttempt(UUID paymentId, String provider) {
        this.paymentId = paymentId;
        this.provider = provider;
        this.status = AttemptStatus.STARTED;
        this.createdAt = LocalDateTime.now();
    }
}