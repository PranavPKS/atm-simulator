package com.atm.controller;

import com.atm.dao.Account;
import com.atm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
public class ATMController {

    @Autowired
    AccountService accountService;

    // inject via application.properties
    @Value("${welcome.message}")
    private String message;

    private List<String> tasks = Arrays.asList("View Balance", "Withdraw", "Deposit", "Transfer", "Exit");

    private String custName; 
    private Account theAccount;
    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("message", message);
        model.addAttribute("tasks", tasks);
        model.addAttribute("account", new Account());

        return "welcome"; //view
    }

    // /hello?name=kotlin
    @GetMapping("/hello")
    public String mainWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {

        custName = name;

        model.addAttribute("message", custName);
        return "welcome"; //view
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Account formAccount, Model model, HttpSession session) {
        int result = accountService.checkLogin(formAccount);
        if (result == 1) {
            model.addAttribute("error", "Incorrect account number or pin");
            System.out.println("Login error");
            return "welcome";
        }
        else {
            //int custId = theAccount.getUserId();
            System.out.println("Login successful");
            session.setAttribute("accountNum", formAccount.getAccountNo());

            return "menu";
        }
    }

    @GetMapping("/balance")
    public String mainBalance(Model model,  HttpSession session) {
        
        float bal = accountService.getAccountByAccountNo((int)session.getAttribute("accountNum")).getBalance();
        model.addAttribute("balance", bal);    
        return "balance"; //view
    }


    @GetMapping("/withdraw")
    public String menu(Model model) {
        model.addAttribute("account", new Account());
        return "withdraw"; //view
    }


    @GetMapping("/accounts")
    private String getAllAccounts(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "welcome";
    }

    @GetMapping("/menu")
    public String goToMenu() {
        return "menu";
    }

    @GetMapping("/transfer")
    public String goToTransfer(Model model) {
        model.addAttribute("account", new Account());
        return "transfer";
    }

    @PostMapping("/transfer")
    public String makeTransfer(@ModelAttribute Account targetAccount, HttpSession session, Model model) {
        boolean result = accountService.makeTransfer((Integer)session.getAttribute("accountNum"),
                targetAccount.getAccountNo(), targetAccount.getBalance());
        if (result) {
            model.addAttribute("message", "Transfer was successful");
        } else {
            model.addAttribute("message", "Low balance. Could not transfer. ");
        }
        return "transfer";
    }

    @PostMapping("/withdraw")
    public String withdrawMoney(@ModelAttribute Account account, HttpSession session, Model model) {
        boolean result = accountService.withdrawMoney((Integer)session.getAttribute("accountNum"),
                account.getBalance());
        if (result) {
            model.addAttribute("message", "You withdrew $" + account.getBalance());
        } else {
            model.addAttribute("message", "Low balance. Could not withdraw. ");
        }
        return "withdraw";
    }

    @GetMapping("/deposit")
    public String deposit(Model model) {
        model.addAttribute("account", new Account());
        return "deposit"; //view
    }

    @PostMapping("/deposit")
    public String depositMoney(@ModelAttribute Account account, HttpSession session, Model model) {
        boolean result = accountService.depositMoney((Integer)session.getAttribute("accountNum"),
                account.getBalance());
        if (result) {
            model.addAttribute("message", "You deposited $" + account.getBalance());
        } else {
            model.addAttribute("message", "Error while depositing.");
        }
        return "deposit";
    }

}