CREATE DATABASE IF NOT EXISTS smartbank;
USE smartbank;

DROP TABLE IF EXISTS transaction;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS staff;
DROP TABLE IF EXISTS admin;

CREATE TABLE admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE staff (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE customer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE account (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    balance DOUBLE NOT NULL DEFAULT 0,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE transaction (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    type ENUM('DEPOSIT', 'WITHDRAW') NOT NULL,
    amount DOUBLE NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES account(id)
);

-- Sample data
INSERT INTO admin (username, password) VALUES ('admin', 'admin123');
INSERT INTO staff (username, password) VALUES ('staff1', 'staff123');
INSERT INTO staff (username, password) VALUES ('staff2', 'staff123');

INSERT INTO customer (username, password) VALUES ('alice', 'pass1');
INSERT INTO customer (username, password) VALUES ('bob', 'pass2');

INSERT INTO account (customer_id, balance)
SELECT id, 500.00 FROM customer WHERE username = 'alice';
INSERT INTO account (customer_id, balance)
SELECT id, 250.00 FROM customer WHERE username = 'bob';

INSERT INTO transaction (account_id, type, amount)
SELECT a.id, 'DEPOSIT', 500.00 FROM account a JOIN customer c ON a.customer_id = c.id WHERE c.username = 'alice';
INSERT INTO transaction (account_id, type, amount)
SELECT a.id, 'DEPOSIT', 250.00 FROM account a JOIN customer c ON a.customer_id = c.id WHERE c.username = 'bob';
