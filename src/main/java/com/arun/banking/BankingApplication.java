package com.arun.banking;

import java.util.Arrays;

import com.arun.banking.entities.User;
import com.arun.banking.model.Role;
import com.arun.banking.services.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BankingApplication implements CommandLineRunner {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(BankingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Add a Super User at start of application
		User user = new User();
		user.setRoles(Arrays.asList(Role.ADMIN, Role.EMPLOYEE));
		user.setEmpId(10000);
		user.setPassword(this.encoder.encode("password"));
		this.employeeService.createSuperUser(user);
	}
}
