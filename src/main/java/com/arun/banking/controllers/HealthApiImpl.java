package com.arun.banking.controllers;

import com.arun.banking.api.HealthApi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller to ensure the Healt of the system
 */
@RestController
public class HealthApiImpl implements HealthApi {

    /**
     * A Simple end point that let us know if the server is up and running
     */
    @Override
    public ResponseEntity<String> getHealthStatus() {
        return new ResponseEntity<>("I am Healthy", HttpStatus.OK);
    }
}
