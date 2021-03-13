package com.arun.banking.repositories;

import com.arun.banking.entities.Account;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface to Query against MongoDb for Account Collection
 */
public interface AccountRepository extends MongoRepository<Account, String> {

    Account findByAccountNumber(Integer accountNumber);
}
