package com.arun.banking.services;

import java.util.List;

import com.arun.banking.entities.Account;
import com.arun.banking.entities.AccountBalanceHistory;
import com.arun.banking.entities.Transaction;
import com.arun.banking.exceptions.BankingError;
import com.arun.banking.exceptions.BankingException;
import com.arun.banking.model.TransactionDetails;
import com.arun.banking.model.TransactionHistoryRequest;
import com.arun.banking.model.TransactionId;
import com.arun.banking.repositories.AccountBalanceHistoryRepository;
import com.arun.banking.repositories.TransactionRepository;
import com.arun.banking.util.AccountLockTracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Handles all transaction related service and business logic around it
 */
@Service
public class TransactionService {
    Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountBalanceHistoryRepository abhRepo;

    @Autowired
    AccountService accountService;

    /**
     * Makes a transaction based on details. From account being valid is not made
     * mandatory.
     * 
     * @param transactionDetails Details of the transaction
     * @return the transaction id for the transaction
     */
    public TransactionId makeTransaction(TransactionDetails transactionDetails) {
        // validate incoming data ...
        logger.info("Initiating transaction for :" + transactionDetails.toString());

        Account fromAccount = null;
        if (null != transactionDetails.getFromAccountNumber()) {
            fromAccount = this.accountService.getAccount(transactionDetails.getFromAccountNumber());
        }
        Account toAccount = this.accountService.getAccount(transactionDetails.getToAccountNumber());

        if (null != fromAccount && (fromAccount.getBalance() < transactionDetails.getFundsToTransfer())) {
            logger.error("Insufficient funds in fromAccount to initiate transfer");
            throw new BankingException(BankingError.ERR_BAD_REQUEST, "Insufficient funds in fromAccount");
        }

        // Allowing from account to be null for insertion from bank interest
        Transaction transaction = new Transaction(transactionDetails);

        // Update Account balance and create a balance history
        this.transactionRepository.insert(transaction);
        // Update balance into from account
        if (null != fromAccount) {
            try {
                synchronized (AccountLockTracker.getLockObject(fromAccount.getAccountNumber())) {
                    // fetching again to operate on correct balance
                    fromAccount = this.accountService.getAccount(transactionDetails.getFromAccountNumber());
                    fromAccount.setBalance(fromAccount.getBalance() - transaction.getFundsToTransfer());
                    this.accountService.updateAccount(fromAccount);
                }
            } finally {
                AccountLockTracker.freeLockObject(fromAccount.getAccountNumber());
            }

        }

        try {
            synchronized (AccountLockTracker.getLockObject(toAccount.getAccountNumber())) {
                // fetching again to operate on correct balance
                toAccount = this.accountService.getAccount(transactionDetails.getToAccountNumber());
                toAccount.setBalance(toAccount.getBalance() + transaction.getFundsToTransfer());
                this.accountService.updateAccount(toAccount);
            }
        } finally {
            AccountLockTracker.freeLockObject(toAccount.getAccountNumber());
        }

        // Update account History for From Account
        if (null != fromAccount) {
            AccountBalanceHistory abhFrom = new AccountBalanceHistory();
            abhFrom.setAccountNumber(fromAccount.getAccountNumber());
            abhFrom.setBalancePostTransaction(fromAccount.getBalance());
            abhFrom.setTransactionId(transaction.getTransactionId());
            abhFrom.setTransactionTime(transaction.getTransactionTime());
            abhFrom.setTransaction(transaction);
            this.abhRepo.save(abhFrom);
        }

        // Update Account History for To Account
        AccountBalanceHistory abhTo = new AccountBalanceHistory();
        abhTo.setAccountNumber(toAccount.getAccountNumber());
        abhTo.setBalancePostTransaction(toAccount.getBalance());
        abhTo.setTransactionId(transaction.getTransactionId());
        abhTo.setTransactionTime(transaction.getTransactionTime());
        abhTo.setTransaction(transaction);
        this.abhRepo.save(abhTo);

        logger.info("transaction completed id:" + transaction.getTransactionId());
        return (new TransactionId()).transactionId(transaction.getTransactionId());
    }

    /**
     * Fetch the Transaction History for account number & Time range
     * 
     * @param histReq request with account number and time range
     * @return Account Balance History List
     */
    public List<AccountBalanceHistory> getTransactionHistory(TransactionHistoryRequest histReq) {
        logger.info("Getting transaction History for Request:" + histReq.toString());
        List<AccountBalanceHistory> abh = this.abhRepo.findTransactionBetweenDateForAccount(histReq.getAccountNumber(),
                histReq.getFromTime(), histReq.getToTime());
        if (abh.size() == 0) {
            logger.error("Unable to fetch data for History for Request:" + histReq.toString());
            throw new BankingException(BankingError.ERR_NOT_FOUND,
                    "Details not found for account number and time range");
        }
        logger.info("Successfully Fetched History");
        return abh;
    }
}
