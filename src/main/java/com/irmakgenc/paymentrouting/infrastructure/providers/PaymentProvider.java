package com.irmakgenc.paymentrouting.infrastructure.providers;

import com.irmakgenc.paymentrouting.domain.model.Payment;

public interface PaymentProvider {

    String name();

    ProviderResult charge(Payment payment);
}
