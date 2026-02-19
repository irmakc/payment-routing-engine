package com.irmakgenc.paymentrouting.application;

import com.irmakgenc.paymentrouting.domain.model.Payment;
import com.irmakgenc.paymentrouting.infrastructure.providers.PaymentProvider;
import com.irmakgenc.paymentrouting.infrastructure.providers.ProviderResult;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RoutingServiceTest {
    static class StubProvider implements PaymentProvider {
        private final String name;
        StubProvider(String name) { this.name = name; }
        @Override public String name() { return name; }
        @Override public ProviderResult charge(Payment payment) { return ProviderResult.success("ok"); }
    }

    @Test
    void high_amount_should_prioritize_safepay() {
        RoutingService routingService = new RoutingService(List.of(
                new StubProvider("FASTPAY"),
                new StubProvider("SAFEPAY"),
                new StubProvider("CHEAPPAY")
        ));

        Payment p = new Payment(new BigDecimal("1500.00"), "EUR", "user-1");
        List<PaymentProvider> order = routingService.route(p);

        assertThat(order).extracting(PaymentProvider::name)
                .startsWith("SAFEPAY");
    }

    @Test
    void eur_should_prioritize_fastpay_when_not_high_amount() {
        RoutingService routingService = new RoutingService(List.of(
                new StubProvider("FASTPAY"),
                new StubProvider("SAFEPAY"),
                new StubProvider("CHEAPPAY")
        ));

        Payment p = new Payment(new BigDecimal("99.00"), "EUR", "user-1");
        List<PaymentProvider> order = routingService.route(p);

        assertThat(order).extracting(PaymentProvider::name)
                .startsWith("FASTPAY");
    }
}
