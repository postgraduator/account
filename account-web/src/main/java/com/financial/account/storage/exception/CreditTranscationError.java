package com.financial.account.storage.exception;

public class CreditTranscationError extends RuntimeException {
    public CreditTranscationError() {
        super("The credit operation is not allowed due to credit amount is more than current account value");
    }
}
