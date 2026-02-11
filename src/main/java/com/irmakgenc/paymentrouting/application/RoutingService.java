package com.irmakgenc.paymentrouting.application;

import com.irmakgenc.paymentrouting.domain.model.Payment;
import com.irmakgenc.paymentrouting.infrastructure.providers.PaymentProvider;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class RoutingService {

    private final Map<String, PaymentProvider> providerByName;

    public RoutingService(List<PaymentProvider> providers) {
        Map<String, PaymentProvider> map = new HashMap<>();
        for (PaymentProvider p : providers) {
            map.put(p.name(), p);
        }
        this.providerByName = Collections.unmodifiableMap(map);
    }

    public List<PaymentProvider> route(Payment payment) {
        // Simple, generic rules (fintech-ish but not real-life specific)
        boolean highAmount = payment.getAmount().compareTo(new BigDecimal("1000")) > 0;
        boolean eur = "EUR".equalsIgnoreCase(payment.getCurrency());

        List<String> order = new ArrayList<>();

        if (highAmount) {
            order.add("SAFEPAY");
            order.add("FASTPAY");
            order.add("CHEAPPAY");
        } else if (eur) {
            order.add("FASTPAY");
            order.add("SAFEPAY");
            order.add("CHEAPPAY");
        } else {
            order.add("CHEAPPAY");
            order.add("FASTPAY");
            order.add("SAFEPAY");
        }

        List<PaymentProvider> result = new ArrayList<>();
        for (String name : order) {
            PaymentProvider p = providerByName.get(name);
            if (p != null) result.add(p);
        }
        return result;
    }
}
