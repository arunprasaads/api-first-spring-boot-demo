package com.arun.banking.controllers;

import javax.validation.Valid;

import com.arun.banking.api.CustomerApi;
import com.arun.banking.model.Customer;
import com.arun.banking.model.CustomerDetails;
import com.arun.banking.model.CustomerId;
import com.arun.banking.model.KYCDetails;
import com.arun.banking.services.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestController entry point for all Customer related service
 */
@RestController
public class CustomerApiImpl implements CustomerApi {

    @Autowired
    CustomerService customerService;

    /**
     * End point to add Customer
     * 
     * @param customerDetails Details of Customer to be added
     * @return created customer id
     */
    @Override
    public ResponseEntity<CustomerId> addCustomer(@Valid CustomerDetails customerDetails) {
        CustomerId customerId = this.customerService.addCustomer(customerDetails);
        return new ResponseEntity<>(customerId, HttpStatus.OK);
    }

    /**
     * End point to link account to a customer
     * 
     * @param customerId    customer id to who account needs to be linked
     * @param accountNumber account number to be linked
     */
    @Override
    public ResponseEntity<Void> linkAccount(Integer customerId, Integer accountNumber) {
        this.customerService.linkAccountToCustomer(customerId, accountNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * End point to update KYC information
     * 
     * @param customerId customer id who's KYC details needs to be updated
     * @param kyCDetails the KYC details to be updated
     */
    @Override
    public ResponseEntity<Void> updateKYC(Integer customerId, @Valid KYCDetails kyCDetails) {
        this.customerService.updateKYC(customerId, kyCDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * End point to fetch Customer Information
     * 
     * @param customerId Customer Id who's details needs to be fetched
     * @return Customer information
     */
    @Override
    public ResponseEntity<Customer> getCustomerDetails(Integer customerId) {
        Customer customer = this.customerService.getCustomer(customerId);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    /**
     * Deletes a customer from system
     * 
     * @param customerId customer id to be deleted
     */
    @Override
    public ResponseEntity<Void> deleteCustomer(Integer customerId) {
        this.customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
