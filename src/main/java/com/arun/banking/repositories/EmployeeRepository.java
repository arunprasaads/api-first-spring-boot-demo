package com.arun.banking.repositories;

import com.arun.banking.entities.Employee;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface to Query against MongoDb for Employee Collection
 */
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    Employee findByEmpId(Integer employeeId);

    Employee findByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, Long dateOfBirth);

}
