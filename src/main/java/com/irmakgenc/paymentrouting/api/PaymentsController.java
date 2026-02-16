package com.irmakgenc.paymentrouting.api;

import com.irmakgenc.paymentrouting.api.dto.CreatePaymentRequest;
import com.irmakgenc.paymentrouting.api.dto.PaymentDetailsResponse;
import com.irmakgenc.paymentrouting.application.PaymentAttemptService;
import com.irmakgenc.paymentrouting.application.PaymentProcessingService;
import com.irmakgenc.paymentrouting.application.PaymentService;
import com.irmakgenc.paymentrouting.domain.model.Payment;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Payments", description = "Payment routing and provider fallback simulation")
@RestController
@RequestMapping("/payments")
public class PaymentsController {

    private final PaymentService paymentService;
    private final PaymentProcessingService processingService;
    private final PaymentAttemptService attemptService;

    public PaymentsController(PaymentService paymentService, PaymentProcessingService processingService, PaymentAttemptService attemptService) {
        this.paymentService = paymentService;
        this.processingService = processingService;
        this.attemptService = attemptService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment create(@RequestBody @Valid CreatePaymentRequest request) {
        Payment created = paymentService.createPayment(request.getAmount(), request.getCurrency(), request.getCustomerId());
        return processingService.process(created.getId());
    }

    @GetMapping("/{id}")
    public PaymentDetailsResponse get(@PathVariable UUID id) {
        var payment = paymentService.getPayment(id);
        var attempts = attemptService.getAttemptsForPayment(id);
        return new PaymentDetailsResponse(payment, attempts);
    }
}
