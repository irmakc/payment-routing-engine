package com.irmakgenc.paymentrouting.api.dto;

import com.irmakgenc.paymentrouting.domain.model.Payment;
import com.irmakgenc.paymentrouting.domain.model.PaymentAttempt;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public class PaymentDetailsResponse {
        private final Payment payment;
        private final List<PaymentAttempt> attempts;

        public PaymentDetailsResponse(Payment payment, List<PaymentAttempt> attempts) {
            this.payment = payment;
            this.attempts = attempts;
        }

        public Payment getPayment() {
            return payment;
        }

        public List<PaymentAttempt> getAttempts() {
            return attempts;
        }
    }
