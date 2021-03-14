# api-first-spring-boot-demo
Creating a Spring boot Rest Service with API first approach. 

# Project details: #
This project design has been developed using API first approach.
All the Rest End Poinds and Models are completely autogenrated based on the open api 3.0 specification.

# Technologies Used: #
Java
Spring boot 
OpenApi 3.0 
MongoDb
JWT Token
gradle - for build process
Postman for testing (postman collection is present in docs folder)

# Launching Application: #
Command for testing project. In project root folder run:
.\gradlew bootRun
Pre-requisites:
MongoDB server needs to be configured in application.properties and should be up and running

# Project Structure: #
src\main\resource - application.properties & bank-api.yaml <br />
com.arun.banking.BankingApplication - Application start <br />
com.arun.banking: <br />
			controllers - all Rest Api end point<br />implementation<br />
			entities - all DB Objects<br />
			exceptions - Exception handling mechanism<br />
			respoitories - Mongo DB respoitory for all querying against mongo db<br />
			security - Scurity Configuration & JWT<br />Authorization Filter
			service - Service Layer inbetween controller (ApiImp) and DB where the business logic is held<br />
			util - Some utility for PDF generation, LockObject handling and Token generation<br />
			
# Key Callouts: #
(1) All use cases have been addressed and only happy paths were covered in testing<br />
(2) SignIn Api will generate the bearer token and this needs to be used in the appropriate api calls (User Role is also part of the token)<br />
(3) SignOut will invalidate the bearer token<br />
Bearer token will auto expire in 10 mins (Hardcoded)<br />
(4) All Api requirements are document and generated from bank-api.yaml<br />
(5) PDF file generation is just a dump of all records in the mongo collection for showing the transaction history (Sorted in descding order)<br />
(6) All time stamps are in epoch milliseconds (including DOB)<br />
(7) Basic Logging done and handled Business exceptions to show my approach towards exception handling <br />
(8) By default the application insert a Super User empId: 10000 and password: password<br />

# Future enhancements that can be done: #
(*) Potential DB exceptions, transaction roll back should all be taken care. <br />
(*) Bearer Tokens are persisted in MongoDb can be moved over to Cache <br />
(*) Exception/Errors in yaml have not been documented
PDF for transaction history could be improved by look feel (though all data is present in it) <br />
(*) Pagination of Transaction <br />
(*) Unit Testing/Code coverage not done due to lack of time<br />
(*) Seperated Response Models (Not done in some scenarios)<br />
(*) Handling access of invalid endpoints should be done more gracefully<br />
(*) Validation of incoming data <br />
(*) no git branching policies were done <br />
(*) Accounting for build Process (Jenkins File etc)<br />
