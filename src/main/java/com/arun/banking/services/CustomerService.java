package com.arun.banking.services;

import java.util.ArrayList;
import java.util.List;

import com.arun.banking.entities.Account;
import com.arun.banking.entities.Customer;
import com.arun.banking.exceptions.BankingError;
import com.arun.banking.exceptions.BankingException;
import com.arun.banking.model.CustomerDetails;
import com.arun.banking.model.CustomerId;
import com.arun.banking.repositories.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Handles all customer related Service and Business logic around it
 */
@Service
public class CustomerService {

    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private static String NEW_CUST_LOCK = "NEW_CUSTOMER_LOCK";

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    IdCounterTrackerService counterService;

    /**
     * Adds a new Customer to the system for the details given
     * 
     * @param customerDetails Details of the customer to be added
     * @return the Customer id created
     */
    public CustomerId addCustomer(CustomerDetails customerDetails) {
        // Validate data if any required ...
        logger.info("Creating new customer with details: " + customerDetails.toString());
        // Search if customer already exists
        Customer customer = this.customerRepository.findByFirstNameAndLastNameAndDateOfBirth(
                customerDetails.getFirstName(), customerDetails.getLastName(), customerDetails.getDateOfBirth());
        if (null == customer) {
            customer = new Customer();
            customer.setFirstName(customerDetails.getFirstName());
            customer.setLastName(customerDetails.getLastName());
            customer.setDateOfBirth(customerDetails.getDateOfBirth());
            customer.setKycDetails(customerDetails.getKycDetails());

            synchronized (NEW_CUST_LOCK) {
                Integer customerId = this.counterService.getNewCustomerId();
                customer.setCustomerId(customerId);
                customer.setAccounts(new ArrayList<Integer>());
                this.customerRepository.save(customer);
                counterService.incrementCounterCustomerId();
            }
        } else {
            logger.error("Customer already exists for the given details: " + customerDetails.toString());
            throw new BankingException(BankingError.ERR_BAD_REQUEST, "Customer already exists");
        }
        logger.info("Customer created with id: " + customer.getCustomerId());
        return new CustomerId().customerId(customer.getCustomerId());
    }

    /**
     * Link an account to a customer
     * 
     * @param customerId    customer id to whom the account should be linked
     * @param accountNumber account number to be linked with customer
     */
    public void linkAccountToCustomer(Integer customerId, Integer accountNumber) {
        logger.info("linking account: " + accountNumber + " to customerId:" + customerId);
        Customer customer = this.customerRepository.findByCustomerId(customerId);
        Account account = this.accountService.getAccount(accountNumber);
        if (null == customer || null == account) {
            logger.error("Invalid account/customer: " + accountNumber + "/" + customerId);
            throw new BankingException(BankingError.ERR_BAD_REQUEST, "Invalid accountNumber/CustomerId");
        } else {
            List<Integer> accounts = customer.getAccounts();
            if (!accounts.contains(accountNumber)) {
                accounts.add(accountNumber);
                customer.accounts(accounts);
                this.customerRepository.save(customer);
            }
        }
        logger.info("linked account: " + accountNumber + " to customerId:" + customerId);
    }

    /**
     * Delete Customer from system
     * 
     * @param customerId customer id who need to be deleted
     */
    public void deleteCustomer(Integer customerId) {
        logger.info("Deleting Customer: " + customerId);
        Customer customer = this.customerRepository.findByCustomerId(customerId);
        if (null == customer) {
            logger.warn("CustomerId doesn't exist: " + customerId);
            throw new BankingException(BankingError.ERR_BAD_REQUEST, "Customer does not exist");
        } else {
            // Delete all associated accounts
            customer.getAccounts().forEach((accountNumber) -> {
                this.accountService.deleteAccount(accountNumber);
            });
            // Delete Customer
            this.customerRepository.delete(customer);
        }
        logger.info("Deleted Customer: " + customerId);
    }
}
