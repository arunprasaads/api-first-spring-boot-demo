package com.arun.banking.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * AccountBalanceHistory Collection extended from AccountBalanceHistory model to
 * make it into an entity. This will track account balance related information.
 * This entity also holds a reference to the transaction entity.
 */
@Document(collection = "AccountBalanceHistory")
public class AccountBalanceHistory extends com.arun.banking.model.AccountBalanceHistory {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    public String getId() {
        return this.id;
    }

    @DBRef(db = "banking")
    private Transaction transaction;

    /**
     * Get transaction details associated with this record as a db ref
     * 
     * @return
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Sets the DB Transaction Entity into Account Balance History
     * 
     * @param transaction transaction entity that needs to linked
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return super.toString() + transaction.toString();
    }
}
