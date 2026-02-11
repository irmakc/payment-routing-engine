package com.irmakgenc.paymentrouting.infrastructure.providers;

import com.irmakgenc.paymentrouting.domain.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class SafePayProvider implements PaymentProvider {

    @Override
    public String name() {
        return "SAFEPAY";
    }

    @Override
    public ProviderResult charge(Payment payment) {
        return ProviderResult.success("SafePay approved");
    }
}

