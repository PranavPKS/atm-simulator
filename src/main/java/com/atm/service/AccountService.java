package com.atm.service;

import com.atm.dao.Account;
import com.atm.dao.AccountRepository;
import com.atm.dao.Transaction;
import com.atm.dao.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * The AccountService class provides business logic for an account
 * @author Shipra Behera
 * @author Pranav Sivakumar
 * @author Vandana Sridhar
 */
@Service
public class AccountService {
    // Tells the application context to inject an instance of AccountRepository here
    @Autowired
    AccountRepository accountRepository;
    // Tells the application context to inject an instance of TransactionRepository here
    @Autowired
    TransactionRepository transactionRepository;


    /**
     * Function to get all accounts from accountRepository
     * @return list of all accounts
     */
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(account -> accounts.add(account));
        return accounts;
    }

    /**
     * Function to get an account from accountRepository
     * @param accNo account number
     * @return account object
     */

    public Account getAccountByAccountNo(int accNo) {
        Account account = accountRepository.findByAccountNo(accNo);
        return account;
    }

    /**
     * Function to save or update an account in the database
     * @param account object
     */
    public void saveOrUpdate(Account account) {
        accountRepository.save(account);
    }

    /**
     * Function to delete an account in the database by account number
     * @param acc_num account number
     */
    public void delete(int acc_num) {
        accountRepository.deleteById(acc_num);
    }

    /**
     * Function to perform login operation of an account
     * Checks for incorrect attempts and locks account
     * @param formAccount account object
     * @return integer denoting the status of the operation
     */
    public int checkLogin(Account formAccount) {
        Account account = getAccountByAccountNo(formAccount.getAccountNo());
        //return 1 if account not found
        if (account == null) {
            return 1;
        } else if (account.getLocked() == 1) {
            return 3; // return 3 if account is locked
        } else if (account.getPin() != formAccount.getPin()) {
                if (account.getIncorrectAttempts() < 2) {
                    account.setIncorrectAttempts(account.getIncorrectAttempts() + 1);
                    saveOrUpdate(account);
                    return 2; //return 2 if incorrect pin but account is not locked yet
                } else {
                    account.setIncorrectAttempts(account.getIncorrectAttempts() + 1);
                    account.setLocked(1);
                    saveOrUpdate(account);
                    return 3; //lock account on 3rd incorrect attempt and return 3
                }

        } else {
            return 0; // return 0 if login successful
        }

    }


    /**
     * Function to check account has sufficient balance
     * @param account object
     * @param amount to be deducted from current balance
     * @return true or false
     */
    public boolean checkIfEnough(Account account, float amount) {
        float remaining = account.getBalance() - amount;
        if (remaining >= 0) {
            return true;
        }
        return false;
    }

    /**
     * Function to transfer money between accounts
     * @param originAccNo current account
     * @param targetAccNo target account
     * @param amount to be transferred
     * @return true or false
     */
    public boolean makeTransfer(int originAccNo, int targetAccNo, float amount) {
        Account origAccount = getAccountByAccountNo(originAccNo);
        Account targetAccount = getAccountByAccountNo(targetAccNo);
        if (checkIfEnough(origAccount, amount)) {
            origAccount.setBalance(origAccount.getBalance() - amount);
            targetAccount.setBalance(targetAccount.getBalance() + amount);
            //update the two accounts after the transaction
            saveOrUpdate(origAccount);
            saveOrUpdate(targetAccount);
            //save transaction to database with current timestamp
            Transaction transaction = new Transaction();
            transaction.setAccountNo(originAccNo);
            transaction.setTime(new Timestamp(new Date().getTime()));
            transaction.setType("transfer from");
            transactionRepository.save(transaction);
            Transaction transaction2 = new Transaction();
            transaction2.setAccountNo(targetAccNo);
            transaction2.setTime(new Timestamp(new Date().getTime()));
            transaction2.setType("transfer to");
            transactionRepository.save(transaction2);

            return true;
        } else {
            return false;
        }
    }

    /**
     * Function to withdraw money from account
     * @param accountNo
     * @param amount
     * @return true or false
     */
    public boolean withdrawMoney(int accountNo, float amount) {
        Account account = getAccountByAccountNo(accountNo);
        //check if account has sufficient balance
        if (checkIfEnough(account, amount)) {
            account.setBalance(account.getBalance() - amount);
            saveOrUpdate(account);
            //save transaction to database
            Transaction transaction = new Transaction();
            transaction.setAccountNo(accountNo);
            transaction.setTime(new Timestamp(new Date().getTime()));
            transaction.setType("withdraw");
            transactionRepository.save(transaction);
            return true;
        }
        return false;
    }

    /**
     * Function to deposit money into an account
     * @param accountNo
     * @param amount
     * @return true or false
     */

    public boolean depositMoney(int accountNo, float amount) {
        Account account = getAccountByAccountNo(accountNo);
        account.setBalance(account.getBalance() + amount);
        saveOrUpdate(account);
        // save transaction to database
        Transaction transaction = new Transaction();
        transaction.setAccountNo(accountNo);
        transaction.setTime(new Timestamp(new Date().getTime()));
        transaction.setType("deposit");
        transactionRepository.save(transaction);
        return true;
    }


}
