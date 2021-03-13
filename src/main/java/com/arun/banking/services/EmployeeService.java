package com.arun.banking.services;

import com.arun.banking.entities.Employee;
import com.arun.banking.entities.User;
import com.arun.banking.exceptions.BankingError;
import com.arun.banking.exceptions.BankingException;
import com.arun.banking.model.EmployeeId;
import com.arun.banking.model.EmployeeUserDetails;
import com.arun.banking.repositories.EmployeeRepository;
import com.arun.banking.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Handles operation on Employee and User collections and associated Business
 * logic
 */
@Service
public class EmployeeService {

    Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private static String NEW_EMP_LOCK = "NEW_EMPLOYEE_LOCK";

    @Autowired
    EmployeeRepository empRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    IdCounterTrackerService counterService;

    /**
     * Adds an employee based on the details
     * 
     * @param empUsrDtls details for the employee to be added
     * @return Returns the Employee Id created for the new Employee
     */
    public EmployeeId addEmployee(EmployeeUserDetails empUsrDtls) {
        logger.info("Processing Add Employee: " + empUsrDtls.toString());
        // Search if such Employee already exists
        Employee emp = this.empRepo.findByFirstNameAndLastNameAndDateOfBirth(empUsrDtls.getFirstName(),
                empUsrDtls.getLastName(), empUsrDtls.getDateOfBirth());
        if (null == emp) {
            User user = new User();
            user.setRoles(empUsrDtls.getRoles());
            // TODO: Password encoding
            user.setPassword(empUsrDtls.getPassword());

            emp = new Employee();
            emp.setFirstName(empUsrDtls.getFirstName());
            emp.setLastName(empUsrDtls.getLastName());
            emp.setDateOfBirth(empUsrDtls.getDateOfBirth());
            emp.setDesignation(empUsrDtls.getDesignation());

            synchronized (NEW_EMP_LOCK) {
                Integer empId = counterService.getNewEmployeeId();
                emp.setEmpId(empId);
                user.setEmpId(empId);
                userRepo.save(user);
                empRepo.save(emp);
                counterService.incrementCounterEmployeeId();
            }
        } else {
            logger.error("Employee Already exists");
            throw new BankingException(BankingError.ERR_BAD_REQUEST, "Bank Emplyee Already exits");
        }
        logger.info("New Employee Created with employee id " + emp.getEmpId());
        return (new EmployeeId()).empId(emp.getEmpId());
    }

    public void deleteEmployee(Integer empId) {
        logger.info("Deleting Employee " + empId);
        Employee emp = this.empRepo.findByEmpId(empId);
        User user = userRepo.findByEmpId(empId);
        if (null == emp || null == user) {
            logger.warn("Employee Doesn't exist in system " + empId);
            throw new BankingException(BankingError.ERR_NOT_FOUND, "Employee Doesn't exist in system");
        } else {
            this.empRepo.delete(emp);
            this.userRepo.delete(user);
        }
        logger.info("Employee Deleted " + empId);
    }
}
