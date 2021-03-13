package com.arun.banking.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Employee Collection extended from Employee model to make it into an entity.
 * This will house all employee related information
 */
@Document(collection = "Employees")
public class Employee extends com.arun.banking.model.Employee {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    public String getId() {
        return this.id;
    }
}
