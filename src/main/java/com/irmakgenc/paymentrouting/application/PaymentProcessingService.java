package com.irmakgenc.paymentrouting.application;

import com.irmakgenc.paymentrouting.domain.enums.AttemptStatus;
import com.irmakgenc.paymentrouting.domain.enums.PaymentStatus;
import com.irmakgenc.paymentrouting.domain.model.Payment;
import com.irmakgenc.paymentrouting.domain.model.PaymentAttempt;
import com.irmakgenc.paymentrouting.infrastructure.persistence.PaymentAttemptRepository;
import com.irmakgenc.paymentrouting.infrastructure.persistence.PaymentRepository;
import com.irmakgenc.paymentrouting.infrastructure.providers.PaymentProvider;
import com.irmakgenc.paymentrouting.infrastructure.providers.ProviderResult;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentProcessingService {

    private final PaymentRepository paymentRepository;
    private final PaymentAttemptRepository attemptRepository;
    private final RoutingService routingService;

    public PaymentProcessingService(
            PaymentRepository paymentRepository,
            PaymentAttemptRepository attemptRepository,
            RoutingService routingService
    ) {
        this.paymentRepository = paymentRepository;
        this.attemptRepository = attemptRepository;
        this.routingService = routingService;
    }

    @Transactional
    public Payment process(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + paymentId));

        payment.setStatus(PaymentStatus.PROCESSING);
        paymentRepository.save(payment);

        List<PaymentProvider> providers = routingService.route(payment);

        for (PaymentProvider provider : providers) {
            PaymentAttempt attempt = new PaymentAttempt(payment.getId(), provider.name());
            attemptRepository.save(attempt);

            ProviderResult result = provider.charge(payment);

            if (result.isSuccess()) {
                attempt.setStatus(AttemptStatus.SUCCESS);
                attempt.setFailureReason(null);
                attemptRepository.save(attempt);

                payment.setProvider(provider.name());
                payment.setStatus(PaymentStatus.SUCCESS);
                return paymentRepository.save(payment);
            } else {
                attempt.setStatus(AttemptStatus.FAILED);
                attempt.setFailureReason(result.getMessage());
                attemptRepository.save(attempt);
            }
        }

        payment.setStatus(PaymentStatus.FAILED);
        return paymentRepository.save(payment);
    }
}
