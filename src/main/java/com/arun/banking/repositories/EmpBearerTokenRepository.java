package com.arun.banking.repositories;

import com.arun.banking.entities.EmpBearerToken;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmpBearerTokenRepository extends MongoRepository<EmpBearerToken, String> {

    EmpBearerToken findByEmpId(Integer empId);

    EmpBearerToken findByBearerToken(String bearerToken);
}
