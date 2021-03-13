package com.arun.banking.controllers;

import javax.validation.Valid;

import com.arun.banking.api.EmployeeApi;
import com.arun.banking.model.EmployeeId;
import com.arun.banking.model.EmployeeUserDetails;
import com.arun.banking.services.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller entry point for all Employee Services
 */
@RestController
public class EmployeeApiImpl implements EmployeeApi {

    @Autowired
    EmployeeService empService;

    /**
     * End point for adding Employee
     */
    @Override
    public ResponseEntity<EmployeeId> addEmployee(@Valid EmployeeUserDetails employeeUserDetails) {
        EmployeeId empId = this.empService.addEmployee(employeeUserDetails);
        return new ResponseEntity<EmployeeId>(empId, HttpStatus.OK);
    }

    /**
     * End point for Deleting Employee
     */
    @Override
    public ResponseEntity<Void> deleteEmployee(Integer empId) {
        this.empService.deleteEmployee(empId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
