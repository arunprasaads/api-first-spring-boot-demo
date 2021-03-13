package com.arun.banking.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Holds all the error information for Banking Business Logic
 */
public enum BankingError {

    /** Different Error Codes */
    ERR_BAD_REQUEST(HttpStatus.BAD_REQUEST, "Incorrect Input"),
    ERR_NOT_FOUND(HttpStatus.NOT_FOUND, "No Information Available"),
    ERR_INTERNAL_ERR(HttpStatus.INTERNAL_SERVER_ERROR, "Error within Server");

    private HttpStatus httpStatus;
    private String message;

    private BankingError(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    /**
     * Get the HTTP Status Code that should be projected for an error
     * 
     * @return the http status code for the error
     */
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    /**
     * Gets the Default error message for this code
     * 
     * @return message for the error code
     */
    public String getMessage() {
        return this.message;
    }
}
