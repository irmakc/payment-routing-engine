package com.irmakgenc.paymentrouting.application;

import com.irmakgenc.paymentrouting.domain.exception.PaymentNotFoundException;
import com.irmakgenc.paymentrouting.domain.model.Payment;
import com.irmakgenc.paymentrouting.domain.model.PaymentAttempt;
import com.irmakgenc.paymentrouting.infrastructure.persistence.PaymentAttemptRepository;
import com.irmakgenc.paymentrouting.infrastructure.persistence.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentAttemptRepository attemptRepository;

    public PaymentService(PaymentRepository paymentRepository, PaymentAttemptRepository attemptRepository) {
        this.paymentRepository = paymentRepository;
        this.attemptRepository = attemptRepository;
    }
    public Payment createPayment(BigDecimal amount, String currency, String customerId, String idempotencyKey) {

        if (idempotencyKey != null) {
            Optional<Payment> existing = paymentRepository.findByIdempotencyKey(idempotencyKey);
            if (existing.isPresent()) {
                return existing.get();
            }
        }

        Payment payment = new Payment(amount, currency, customerId);
        payment.setIdempotencyKey(idempotencyKey);

        return paymentRepository.save(payment);
    }

    public Payment getPayment(UUID id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id.toString()));
    }
}
