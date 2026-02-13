package com.irmakgenc.paymentrouting.application;

import com.irmakgenc.paymentrouting.domain.model.PaymentAttempt;
import com.irmakgenc.paymentrouting.infrastructure.persistence.PaymentAttemptRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentAttemptService {

    private final PaymentAttemptRepository attemptRepository;

    public PaymentAttemptService(PaymentAttemptRepository attemptRepository) {
        this.attemptRepository = attemptRepository;
    }

    public List<PaymentAttempt> getAttemptsForPayment(UUID paymentId) {
        return attemptRepository.findByPaymentIdOrderByCreatedAtAsc(paymentId);
    }
}