package com.arun.banking.entities;

import com.arun.banking.model.TransactionDetails;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Transaction Collection extended from Transaction model to make it into an
 * entity. This will house all Transaction related information
 */
@Document(collection = "Transaction")
public class Transaction extends com.arun.banking.model.Transaction {

    private static final long serialVersionUID = 1L;

    @Id
    private String transactionId;

    public String getTransactionId() {
        return this.transactionId;
    }

    /**
     * Constructor to allow DB to create an object
     */
    public Transaction() {
    }

    /**
     * Create a transaction object from transaction details
     * 
     * @param transactionDetails Transaction Details that needs to be transformed
     */
    public Transaction(TransactionDetails transactionDetails) {
        this.setFromAccountNumber(transactionDetails.getFromAccountNumber());
        this.setToAccountNumber(transactionDetails.getToAccountNumber());
        this.setFundsToTransfer(transactionDetails.getFundsToTransfer());
        this.setTransactionDescription(transactionDetails.getTransactionDescription());
        this.setTransactionTime(System.currentTimeMillis());
    }
}
