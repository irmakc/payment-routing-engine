package com.irmakgenc.paymentrouting.infrastructure.providers;

import com.irmakgenc.paymentrouting.domain.model.Payment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FastPayProvider implements PaymentProvider {

    @Override
    public String name() {
        return "FASTPAY";
    }

    @Override
    public ProviderResult charge(Payment payment) {
        // Simülasyon: yüksek tutarlarda ara sıra başarısız olsun
        if (payment.getAmount().compareTo(new BigDecimal("1000")) > 0) {
            return ProviderResult.failure("FastPay rejected high amount");
        }
        return ProviderResult.success("FastPay approved");
    }
}
