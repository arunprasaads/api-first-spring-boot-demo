package com.arun.banking.services;

import com.arun.banking.entities.IdCounterTracker;
import com.arun.banking.exceptions.BankingError;
import com.arun.banking.exceptions.BankingException;
import com.arun.banking.repositories.IdCounterTrackerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Helper service which is going to maintaining fetching getting IDs for
 * specific purpose
 */
@Service
public class IdCounterTrackerService {

    Logger logger = LoggerFactory.getLogger(IdCounterTrackerService.class);

    @Autowired
    IdCounterTrackerRepository counterRepo;

    private static String EMP_COUNTER_FIELD = "LAST_CREATED_EMPLOYEE_ID";
    private static String ACC_COUNTER_FIELD = "LAST_CREATED_ACCOUNT_ID";
    private static String CUST_COUNTER_FIELD = "LAST_CREATED_CUSTOMER_ID";

    /**
     * 
     * @return Gets the new Employee Id
     */
    public Integer getNewEmployeeId() {
        return getNewIdForField(EMP_COUNTER_FIELD, 10000);
    }

    /**
     * 
     * @return gets the new Customer Id
     */
    public Integer getNewCustomerId() {
        return getNewIdForField(CUST_COUNTER_FIELD, 10000);
    }

    /**
     * 
     * @return gets the new Account Number
     */
    public Integer getNewAccountNumber() {
        return getNewIdForField(ACC_COUNTER_FIELD, 10000);
    }

    /**
     * Increments the Emplyee id in the tracker
     */
    public void incrementCounterEmployeeId() {
        incrementForField(EMP_COUNTER_FIELD);
    }

    /**
     * Increments the counter for Customer Id
     */
    public void incrementCounterCustomerId() {
        incrementForField(CUST_COUNTER_FIELD);
    }

    /**
     * Increments the counter for Account Number
     */
    public void incrementCounterAccountNumber() {
        incrementForField(ACC_COUNTER_FIELD);
    }

    /**
     * Get the new Id for the given field
     * 
     * @param field        field name for which new id needs to fetched
     * @param defaultValue the initial entry in case no value was set for this field
     * @return Returns the new value that can be used
     */
    private Integer getNewIdForField(String field, Integer defaultValue) {
        IdCounterTracker counterInfo = this.counterRepo.findByCounterField(field);
        if (null == counterInfo) {
            counterInfo = new IdCounterTracker(field, defaultValue);
            this.counterRepo.save(counterInfo);
        }
        return counterInfo.getCounterValue() + 1;
    }

    /**
     * Increment the Id for the field
     * 
     * @param field field for which id needs to be increments
     */
    private void incrementForField(String field) {
        IdCounterTracker counterInfo = this.counterRepo.findByCounterField(field);
        if (null == counterInfo) {
            logger.error("Unable to field counter for Field: " + field);
            throw new BankingException(BankingError.ERR_INTERNAL_ERR,
                    field + " Field to be incremented is not found in db");
        } else {
            counterInfo.setCounterValue(counterInfo.getCounterValue() + 1);
            this.counterRepo.save(counterInfo);
        }
    }
}
