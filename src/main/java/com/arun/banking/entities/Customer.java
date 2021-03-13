package com.arun.banking.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Customer Collection extended from Customer model to make it into an entity.
 * This will house all customer related information
 */
@Document(collection = "Customer")
public class Customer extends com.arun.banking.model.Customer {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    public String getId() {
        return this.id;
    }
}
