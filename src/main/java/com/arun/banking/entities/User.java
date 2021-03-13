package com.arun.banking.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User Collection extend from User model to be made into an entity This
 * collection will house information for Authentication and Authorisation
 */
@Document(collection = "Users")
public class User extends com.arun.banking.model.User {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    public String getId() {
        return this.id;
    }
}
