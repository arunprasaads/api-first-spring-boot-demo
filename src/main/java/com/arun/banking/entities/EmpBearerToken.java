package com.arun.banking.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity Object that maintains a mapping of EmpId vs his Token
 */
@Document(collection = "EmpBearerToken")
public class EmpBearerToken extends com.arun.banking.model.EmpBearerToken {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    public String getId() {
        return this.id;
    }
}
