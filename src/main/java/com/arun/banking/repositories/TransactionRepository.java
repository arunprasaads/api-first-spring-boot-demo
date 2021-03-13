package com.arun.banking.repositories;

import com.arun.banking.entities.Transaction;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface to Query against MongoDb for Transaction Collection
 */
public interface TransactionRepository extends MongoRepository<Transaction, String> {

}
