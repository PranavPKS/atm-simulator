package com.atm.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * TransactionRepository interface provides the mechanism for CRUD operations on the database
 * Extends the CrudRepository interface
 * Type of Entity and Id that it works with, Transaction and Integer are specified as generic parameters
 * on CrudRepository
 * By extending CrudRepository, TransactionRepository inherits several methods for working with
 * Transaction persistence, including methods for finding, saving, deleting Account entities.
 * Spring creates an implementation on the fly when we run the application
 *
 * @author Shipra Behera
 */
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    /**
     * Function to save a transaction to the database
     * @param t transaction object
     * @return
     */
    Transaction save(Transaction t);

    /**
     * Function to get all transactions
     * @return
     */
    List<Transaction> findAll();

    /**
     * Function to get a transaction by account number
     * @param accountNo
     * @return
     */
    List<Transaction> findByAccountNo(int accountNo);
}
