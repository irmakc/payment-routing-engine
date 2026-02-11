package com.irmakgenc.paymentrouting.infrastructure.providers;

import com.irmakgenc.paymentrouting.domain.model.Payment;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class CheapPayProvider implements PaymentProvider {

    @Override
    public String name() {
        return "CHEAPPAY";
    }

    @Override
    public ProviderResult charge(Payment payment) {
        // Simülasyon: %30 ihtimalle fail
        boolean fail = ThreadLocalRandom.current().nextInt(100) < 30;
        if (fail) {
            return ProviderResult.failure("CheapPay temporary failure");
        }
        return ProviderResult.success("CheapPay approved");
    }
}
