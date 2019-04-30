package com.atm.dao;


import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Transaction class that can be mapped to the Transactions table in the database
 * Design Pattern: This is the Model part of the MVC pattern
 * @author Shipra Behera
 */

@Entity
@Table(name = "Transactions")
public class Transaction {
    //primary key which is auto incremented
    @Id @GeneratedValue (strategy=GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = false)
    private int transactionId;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "transaction_time", nullable = false)
    private Timestamp time;

    @Column(name = "account_no", nullable = false)
    private int accountNo;

    //getters and setters

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }
}