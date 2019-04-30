package com.atm.dao;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

/**
 * Account class that can be mapped to the Accounts table in the database
 * Design Pattern: This is the Model part of the MVC pattern
 * @author Shipra Behera
 */

@Entity
@Table(name = "Accounts")
public class Account {

    //@Id annotation marks the primary key field
    @Id
    @Column(name = "account_no", nullable = false)
    private int accountNo;

    @Column(name = "pin", nullable = false)
    private int pin;

    @Column(name = "balance", nullable = false)
    private float balance;

    @Column(name = "account_type", nullable = false)
    private String type;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "locked", nullable = false)
    private int locked;

    @Column(name = "incorrect_attempts", nullable = false)
    private int incorrectAttempts;

    //getters and setters
    public int getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public int getIncorrectAttempts() {
        return incorrectAttempts;
    }

    public void setIncorrectAttempts(int incorrectAttempts) {
        this.incorrectAttempts = incorrectAttempts;
    }
}
