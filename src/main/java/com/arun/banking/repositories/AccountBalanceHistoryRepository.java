package com.arun.banking.repositories;

import java.util.List;

import com.arun.banking.entities.AccountBalanceHistory;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Interface to Query against MongoDb for AccountBalanceHistory Collection
 */
public interface AccountBalanceHistoryRepository extends MongoRepository<AccountBalanceHistory, String> {

    List<AccountBalanceHistory> findByAccountNumber(Integer accountNumber);

    @Query(value = "{'accountNumber': ?0, 'transactionTime':  { $gte: ?1, $lte: ?2 } }", sort = "{'transactionTime': -1}")
    List<AccountBalanceHistory> findTransactionBetweenDateForAccount(Integer accountNumber, Long fromDate, Long toDate);
}
