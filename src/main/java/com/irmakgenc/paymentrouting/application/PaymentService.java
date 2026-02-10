package com.irmakgenc.paymentrouting.application;

import com.irmakgenc.paymentrouting.domain.model.Payment;
import com.irmakgenc.paymentrouting.infrastructure.persistence.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment createPayment(BigDecimal amount, String currency, String customerId) {
        Payment payment = new Payment(amount, currency, customerId);
        return paymentRepository.save(payment);
    }
}
