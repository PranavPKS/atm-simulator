package com.atm.service;

import com.atm.dao.Account;
import com.atm.dao.AccountRepository;
import com.atm.dao.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<Account>();
        accountRepository.findAll().forEach(account -> accounts.add(account));
        return accounts;
    }
    
}