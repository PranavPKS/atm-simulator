package com.atm.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.atm.dao.Account;

import java.util.List;

/**
 * AccountRepository interface provides the mechanism for CRUD operations on the database
 * Extends the CrudRepository interface
 * Type of Entity and Id that it works with, Account and Integer are specified as generic parameters
 * on CrudRepository
 * By extending CrudRepository, AccountRepository inherits several methods for working with
 * Account persistence, including methods for finding, saving, deleting Account entities.
 * Spring creates an implementation on the fly when we run the application
 *
 * @author Shipra Behera
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Integer>{
    /**
     * Function to find an account by account number
     * @param accountNo
     * @return account object
     */
    Account findByAccountNo(int accountNo);

    /**
     * Function to fina all account
     * @return list of accounts
     */
    List<Account> findAll();

    /**
     * Function to save or update an account to database
     * @param a account object
     * @return
     */
    Account save(Account a);

    /**
     * Function to delete an account by account id
     * @param accNum
     * @return
     */
    Account deleteById(int accNum);
}
