package com.irmakgenc.paymentrouting.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CreatePaymentRequest {
    @Schema(example = "120.50", description = "Payment amount")
    @NotNull
    @Positive
    private BigDecimal amount;

    @Schema(example = "EUR", description = "ISO 4217 currency code")
    @NotBlank
    private String currency;

    @Schema(example = "user-123", description = "Customer identifier")
    @NotBlank
    private String customerId;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

}
