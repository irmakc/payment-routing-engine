package com.irmakgenc.paymentrouting.api;

import com.irmakgenc.paymentrouting.api.dto.CreatePaymentRequest;
import com.irmakgenc.paymentrouting.application.PaymentService;
import com.irmakgenc.paymentrouting.domain.model.Payment;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    private final PaymentService paymentService;

    public PaymentsController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment create(@RequestBody @Valid CreatePaymentRequest request) {
        return paymentService.createPayment(
                request.getAmount(),
                request.getCurrency(),
                request.getCustomerId()
        );
    }
}
