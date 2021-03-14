package com.arun.banking.controllers;

import javax.validation.Valid;

import com.arun.banking.api.AuthApi;
import com.arun.banking.exceptions.BankingError;
import com.arun.banking.exceptions.BankingException;
import com.arun.banking.model.SigninRequest;
import com.arun.banking.model.SigninResponse;
import com.arun.banking.model.User;
import com.arun.banking.services.EmployeeService;
import com.arun.banking.util.JWTTokenGen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller Entry point for sign in/out
 */
@RestController
public class AuthApiImpl implements AuthApi {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * End point for Sign in and have bearer token generated
     * 
     * @param signinRequest EmpId and Password information to generate bearer token
     * @return Bearer Token
     */
    @Override
    public ResponseEntity<SigninResponse> signIn(@Valid SigninRequest signinRequest) {
        User user = this.employeeService.getUserInfo(signinRequest.getEmpId());
        if (!this.passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
            throw new BankingException(BankingError.ERR_NOT_FOUND, "Invalid Credentials");
        }
        String token = (new JWTTokenGen()).getJWTToken(user);
        this.employeeService.updateBearerToken(user.getEmpId(), token);
        SigninResponse resp = new SigninResponse();
        resp.setToken(token);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    /**
     * End point for Sign Out
     * 
     * @param empId EmpId that needs to be signed out
     */
    @Override
    public ResponseEntity<Void> signOut(Integer empId) {
        this.employeeService.deleteBearerTokenForEmpId(empId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
