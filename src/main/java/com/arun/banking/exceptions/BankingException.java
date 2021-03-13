package com.arun.banking.exceptions;

/**
 * The Class that governs the business exception thrown
 */
public class BankingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final BankingError bankingError;

    public BankingException(BankingError error, String errorMessage) {
        super(errorMessage);
        this.bankingError = error;
    }

    /**
     * Fetches the Error Code associated with the Banking error
     * 
     * @return Banking error code
     */
    public BankingError getBankingError() {
        return this.bankingError;
    }
}
