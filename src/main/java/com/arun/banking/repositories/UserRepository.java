package com.arun.banking.repositories;

import com.arun.banking.entities.User;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface to Query against MongoDb for User Collection
 */
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmpId(Integer empId);

}
