# ATM Simulator
<img src="atm.png" alt="drawing" width="350"/>

## Team Members:
Shipra Behera, Pranav Sivakumar, Vandana Sridhar

## Project Overview:
Utilize object-oriented principles and provide a complete design, analysis and programming simulation of a real-world use case - the Automated Teller Machine. We have designed the application as a web application using Java Spring Boot, H2 database, Thymleaf, HTML and Javascript. 
A bank allows its users to perform basic financial transactions and users can have different accounts simultaneously at the bank. Some of the operations the users perform are: to view their account balance, deposit funds, withdraw cash, and transfer cash to other accounts. After successful authentication, the customer can withdraw the desired amount (within the prescribed limit) from the ATM machine or can transfer amount to other account. 

## Description of Files:
* `pom.xml` contains all the maven dependencies for Spring boot, Thymeleaf and H2 database.
* `src`: has the source code for this web application
* `src/main/java/atm` has three folders - 
* `/controller`:
  * `ATMController.java` - ATMController class which acts as a web request handler.
* `/dao`:
  * `Account.java` - Account class which is  mapped to the Accounts table in the database.
  * `AccountRepository.java` - Interface which provides the mechanism for CRUD operations on Accounts.
  * `Transaction.java` - Transaction which is mapped to the Transactions table in the database.
  * `AccountRepository.java` - Interface which provides the mechanism for CRUD operations on Transactions.
* `/service`:
  * `AccountService.java` - Class which provides business logic for an account.
  * `TransactionService.java` - Class which provides business logic for a transaction/log.
* `src/main/resources` has -
* `static` - which contains css, js folders
* `templates` - which contains all the HTML files
* `application.properties` - which has the H2 database settings
* `data.sql` - which has the SQL script which gets executed when the application starts
  
## Installing and Executing
This is a Maven project so that needs to be installed first:
https://maven.apache.org/install.html

After installing Maven, simply follow the steps below to run the web application:
```shell
$ git clone https://github.com/shiprabehera/atm-simulator.git
$ cd atm-simulator
$ mvn spring-boot:run
```
* Open http://localhost:8080 on browser
* Open http://localhost:8080/h2-console to login to the database:
  1. JDBC URL: jdbc:h2:mem:testdb
  2. Username: sa
  3. Password: password


