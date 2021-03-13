package com.arun.banking.repositories;

import com.arun.banking.entities.Customer;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface to Query against MongoDb for Customer Collection
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {

    Customer findByCustomerId(Integer customerId);

    Customer findByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, Long dateOfBirth);
}
