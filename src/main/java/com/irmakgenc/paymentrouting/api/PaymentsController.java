package com.irmakgenc.paymentrouting.api;

import com.irmakgenc.paymentrouting.api.dto.CreatePaymentRequest;
import com.irmakgenc.paymentrouting.application.PaymentProcessingService;
import com.irmakgenc.paymentrouting.application.PaymentService;
import com.irmakgenc.paymentrouting.domain.model.Payment;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    private final PaymentService paymentService;
    private final PaymentProcessingService processingService;

    public PaymentsController(PaymentService paymentService, PaymentProcessingService processingService) {
        this.paymentService = paymentService;
        this.processingService = processingService;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment create(@RequestBody @Valid CreatePaymentRequest request) {
        Payment created = paymentService.createPayment(
                request.getAmount(),
                request.getCurrency(),
                request.getCustomerId()
        );
        return processingService.process(created.getId());
    }
}
