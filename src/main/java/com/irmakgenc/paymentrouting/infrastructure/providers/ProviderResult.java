package com.irmakgenc.paymentrouting.infrastructure.providers;

public class ProviderResult {
    private final boolean success;
    private final String message;

    private ProviderResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static ProviderResult success(String message) {
        return new ProviderResult(true, message);
    }

    public static ProviderResult failure(String message) {
        return new ProviderResult(false, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
