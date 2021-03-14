package com.arun.banking.controllers;

import com.arun.banking.api.AccountApi;
import com.arun.banking.model.Account;
import com.arun.banking.model.AccountNumber;
import com.arun.banking.services.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestController entry point for all Acount Services
 */
@RestController
public class AccountApiImpl implements AccountApi {

    @Autowired
    AccountService accountService;

    /**
     * End point to create account
     * 
     * @param accoutType type of account to be created
     * @return the account number created
     */
    @Override
    public ResponseEntity<AccountNumber> createAccount(String accountType) {
        AccountNumber accountNumber = this.accountService.createAccount(accountType);
        return new ResponseEntity<>(accountNumber, HttpStatus.OK);
    }

    /**
     * Endpoint for fetching account details
     * 
     * @param accountNumber account number for which details needs to be fetched
     * @return get account information
     */
    @Override
    public ResponseEntity<Account> getAccountDetails(Integer accountNumber) {
        Account accountInfo = this.accountService.getAccount(accountNumber);
        return new ResponseEntity<>(accountInfo, HttpStatus.OK);
    }

    /**
     * End point for adding interest
     * 
     * @param accountNumber account number to which interest needs to be added
     */
    @Override
    public ResponseEntity<Void> addInterest(Integer accountNumber) {
        this.accountService.addInterest(accountNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * End point to delete account
     * 
     * @param accountNumber account number to be deleted
     */
    @Override
    public ResponseEntity<Void> deleteAccount(Integer accountNumber) {
        this.accountService.deleteAccount(accountNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
