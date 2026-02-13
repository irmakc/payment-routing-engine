package com.irmakgenc.paymentrouting.application;

import com.irmakgenc.paymentrouting.domain.model.Payment;
import com.irmakgenc.paymentrouting.domain.model.PaymentAttempt;
import com.irmakgenc.paymentrouting.infrastructure.persistence.PaymentAttemptRepository;
import com.irmakgenc.paymentrouting.infrastructure.persistence.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentAttemptRepository attemptRepository;

    public PaymentService(PaymentRepository paymentRepository, PaymentAttemptRepository attemptRepository) {
        this.paymentRepository = paymentRepository;
        this.attemptRepository = attemptRepository;
    }
    public Payment createPayment(BigDecimal amount, String currency, String customerId) {
        Payment payment = new Payment(amount, currency, customerId);
        Payment saved = paymentRepository.save(payment);

        // attempt history starts here; routing will set real provider later
        attemptRepository.save(new PaymentAttempt(saved.getId(), "UNASSIGNED"));

        return saved;
    }

    public Payment getPayment(UUID id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + id));
    }
}
