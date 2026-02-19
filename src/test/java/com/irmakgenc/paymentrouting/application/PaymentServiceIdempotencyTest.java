package com.irmakgenc.paymentrouting.application;

import com.irmakgenc.paymentrouting.domain.model.Payment;
import com.irmakgenc.paymentrouting.infrastructure.persistence.PaymentRepository;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.math.BigDecimal;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentServiceIdempotencyTest {

    @Test
    void should_return_existing_payment_when_idempotency_key_already_used() {
        PaymentRepository repo = mock(PaymentRepository.class);

        Payment existing = new Payment(new BigDecimal("10.00"), "EUR", "user-1");
        existing.setIdempotencyKey("key-123");

        when(repo.findByIdempotencyKey("key-123")).thenReturn(Optional.of(existing));

        PaymentService service = new PaymentService(repo);

        Payment result = service.createPayment(new BigDecimal("10.00"), "EUR", "user-1", "key-123");

        // save should NOT be called because existing is returned
        verify(repo, never()).save(any(Payment.class));
        verify(repo, times(1)).findByIdempotencyKey("key-123");
    }
}
