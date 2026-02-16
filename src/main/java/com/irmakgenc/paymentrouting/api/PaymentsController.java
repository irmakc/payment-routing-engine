package com.irmakgenc.paymentrouting.api;

import com.irmakgenc.paymentrouting.api.dto.CreatePaymentRequest;
import com.irmakgenc.paymentrouting.api.dto.PaymentDetailsResponse;
import com.irmakgenc.paymentrouting.application.PaymentAttemptService;
import com.irmakgenc.paymentrouting.application.PaymentProcessingService;
import com.irmakgenc.paymentrouting.application.PaymentService;
import com.irmakgenc.paymentrouting.domain.model.Payment;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "Create and process a payment",
            description = "Creates a payment and runs routing + provider fallback simulation. Returns the final payment state. Supports Idempotency-Key header to prevent duplicate payments"
    )
    @PostMapping
    public Payment create(
            @RequestBody @Valid CreatePaymentRequest request, @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey
    ) {
        Payment created = paymentService.createPayment(request.getAmount(), request.getCurrency(), request.getCustomerId(), idempotencyKey
        );

        return processingService.process(created.getId());
    }

    @Operation(
            summary = "Get payment details",
            description = "Returns payment information along with provider attempt history."
    )
    @GetMapping("/{id}")
    public PaymentDetailsResponse get(@PathVariable UUID id) {
        var payment = paymentService.getPayment(id);
        var attempts = attemptService.getAttemptsForPayment(id);
        return new PaymentDetailsResponse(payment, attempts);
    }
}
