DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id INT PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL
);

INSERT INTO users (id, first_name, last_name) VALUES
    (1, 'Shipra', 'Behera'),
    (2, 'Vandana', 'Sridhar'),
    (3, 'Pranav', 'Sivakumar');

DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts(
 account_no BIGINT PRIMARY KEY,
 pin INT,
 account_type VARCHAR(10),
 balance REAL,
 user_id INT,
 locked BIT NOT NULL DEFAULT 0,
 incorrect_attempts INT DEFAULT 0,
 foreign key (user_id) references users(id)
);

INSERT INTO accounts(account_no, pin, account_type, balance, user_id) VALUES
    (23456, 1212, 'SAVINGS', 4112, 1);
INSERT INTO accounts(account_no, pin, account_type, balance, user_id) VALUES
    (76345, 8001, 'CHECKING', 5234, 2);

DROP TABLE IF EXISTS transactions;

CREATE TABLE transactions(
 transaction_id INT AUTO_INCREMENT PRIMARY KEY,
 type VARCHAR(20),
 transaction_time TIMESTAMP,
 account_no BIGINT,
 foreign key (account_no) references accounts(account_no)
);