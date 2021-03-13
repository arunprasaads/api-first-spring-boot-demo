package com.arun.banking.services;

import com.arun.banking.entities.Account;
import com.arun.banking.exceptions.BankingError;
import com.arun.banking.exceptions.BankingException;
import com.arun.banking.model.AccountNumber;
import com.arun.banking.repositories.AccountRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Handles all Account related service and Business logic around it
 */
@Service
public class AccountService {

    Logger logger = LoggerFactory.getLogger(AccountService.class);

    private static String NEW_ACC_LOCK = "NEW_ACCOUNT_LOCK";

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    IdCounterTrackerService counterService;

    /**
     * Creates a new account given an account type
     * 
     * @param accountType the type of account to be created
     * @return returns the account number of the account created
     */
    public AccountNumber createAccount(String accountType) {
        logger.info("Creating new account of Type: " + accountType);
        Account account = new Account();
        account.setAccountType(accountType);

        synchronized (NEW_ACC_LOCK) {
            account.setAccountNumber(this.counterService.getNewAccountNumber());
            account.setBalance(0.0f);
            accountRepository.save(account);
            this.counterService.incrementCounterAccountNumber();
        }
        logger.info("New account Created with number: " + account.getAccountNumber());
        return (new AccountNumber()).accountNumber(account.getAccountNumber());
    }

    /**
     * Given an account number returns the account information
     * 
     * @param accountNumber account number of the account
     * @return Account information
     */
    public Account getAccount(Integer accountNumber) {
        return this.accountRepository.findByAccountNumber(accountNumber);
    }

    /**
     * Updates an account into the system
     * 
     * @param account Account information to be updated
     */
    public void updateAccount(Account account) {
        if (null == account.getId()) {
            logger.error("Not a valid Account entity from DB");
            throw new BankingException(BankingError.ERR_INTERNAL_ERR, "Invalid account attempted to be updated");
        }
        this.accountRepository.save(account);
    }

    public void deleteAccount(Integer accountNumber) {
        logger.info("Deleting account: " + accountNumber);
        Account account = this.accountRepository.findByAccountNumber(accountNumber);
        if (null == account) {
            logger.warn("Employee Doesn't exist in system " + accountNumber);
            throw new BankingException(BankingError.ERR_NOT_FOUND, "Account Doesn't exist in system");
        } else {
            this.accountRepository.delete(account);
        }
        logger.info("Account Deleted: " + accountNumber);
    }
}
