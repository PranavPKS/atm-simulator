package com.atm.service;

import com.atm.dao.Transaction;
import com.atm.dao.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The TransactionService class provides business logic for an account
 * @author Shipra Behera
 * @author Pranav Sivakumar
 * @author Vandana Sridhar
 */
@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    /**
     * Function to save a transaction to the repository
     * @param transaction
     */
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
