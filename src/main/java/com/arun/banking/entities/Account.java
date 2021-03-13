package com.arun.banking.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Account Collection extended from Account model to make it into an entity.
 * This will house all account related information
 */
@Document(collection = "Account")
public class Account extends com.arun.banking.model.Account {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    public String getId() {
        return this.id;
    }
}
