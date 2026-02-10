package com.irmakgenc.paymentrouting.infrastructure.persistence;

import com.irmakgenc.paymentrouting.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}