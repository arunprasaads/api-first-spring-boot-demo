package com.arun.banking.controllers;

import com.arun.banking.api.HealthApi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthApiImpl implements HealthApi {

    @Override
    public ResponseEntity<String> getHealthStatus() {
        return new ResponseEntity<>("I am Healthy", HttpStatus.OK);
    }
}
