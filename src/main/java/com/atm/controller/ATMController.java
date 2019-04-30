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

/**
 * The ATMController class which acts as a web request handler
 * Acts as a stereotype for the annotated class.
 * The dispatcher scans such annotated classes for mapped methods
 * and detects the @GettMapping and @PostMapping annotations.
 *
 * Design Pattern: This is the Controller part of the MVC pattern
 * @author Shipra Behera
 * @author Pranav Sivakumar
 * @author Vandana Sridhar
 */
@Controller
public class ATMController {

    // Tells the application context to inject an instance of AccountService here
    @Autowired
    AccountService accountService;

    // inject via application.properties
    @Value("${welcome.message}")
    private String message;

    private List<String> tasks = Arrays.asList("View Balance", "Withdraw", "Deposit", "Transfer", "Exit");

    private String custName; 
    private Account theAccount;

    /**
     * @GetMapping is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET)
     * This function will handle all requests with url "/"
     * @param model Model object which can supply attributes for rendering views
     * an instance of account is created and passed into model, which will be used as a thymeleaf object in our view
     * @return String which is the name of the HTML file to be viewed
     * @author Shipra Behera
     */
    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("message", message);
        model.addAttribute("tasks", tasks);
        model.addAttribute("account", new Account());

        return "welcome"; //view
    }

    /**
     * test function
     * @param name
     * @param model
     * @return
     */
    @GetMapping("/hello")
    public String mainWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {

        custName = name;

        model.addAttribute("message", custName);
        return "welcome"; //view
    }

    /**
     * Handles POST request for /login
     * @param formAccount contains account number and PIN
     * @param model Model object which can store login error messages while rendering the view
     * @param session HTTPSession object to store account number in the session
     * @return String which is the name of the HTML file to be viewed
     * @author Shipra Behera
     */
    @PostMapping("/login")
    public String login(@ModelAttribute Account formAccount, Model model, HttpSession session) {
        //callling the appropriate service function which performs login
        int result = accountService.checkLogin(formAccount);
        //depending on the return value, add messages to the model which will be viewed
        if (result == 1) {
            model.addAttribute("error", "Account number does not exist");
            System.out.println("Login error");
            return "welcome";
        } else if (result == 2) {
            model.addAttribute("error", "Incorrect pin");
            System.out.println("Login error");
            return "welcome";
        } else if (result == 3) {
            model.addAttribute("error", "Account locked");
            System.out.println("Login error");
            return "welcome";
        }
        else {
            System.out.println("Login successful");
            //set session if login is successful and return menu page
            session.setAttribute("accountNum", formAccount.getAccountNo());
            return "menu";
        }
    }

    /**
     * Handles GET request for /balance
     * @param model Model object which stores the balance to view
     * @param session HTTPSession object to retrieve the account number from the session
     * @return String which is the name of the HTML file to be viewed
     * @author Pranav Sivakumar
     */
    @GetMapping("/balance")
    public String mainBalance(Model model,  HttpSession session) {
        
        float bal = accountService.getAccountByAccountNo((int)session.getAttribute("accountNum")).getBalance();
        model.addAttribute("balance", bal);    
        return "balance"; //view
    }

    /**
     * Handles GET request for /withdraw
     * @param model Model object which stores the account number to view
     * @param session HTTPSession object to retrieve the account number from the session
     * @return String which is the name of the HTML file to be viewed
     * @author Shipra Behera
     */
    @GetMapping("/withdraw")
    public String menu(Model model, HttpSession session) {
        model.addAttribute("account", new Account());
        int accnum = (int)session.getAttribute("accountNum");
        model.addAttribute("accnum", accnum);
        return "withdraw"; //view
    }


    /**
     * test function
     * Handles GET request for /accounts
     * @@param model Model object which stores the accounts to view
     * @return String which is the name of the HTML file to be viewed
     */
    @GetMapping("/accounts")
    private String getAllAccounts(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "welcome";
    }

    /**
     * Handles GET request for /menu
     * @return menu HTML file name as a String
     */

    @GetMapping("/menu")
    public String goToMenu() {
        return "menu";
    }

    /**
     * Handles GET request for /transfer
     * @param model
     * @return transfer HTML filename as a String
     */
    @GetMapping("/transfer")
    public String goToTransfer(Model model) {
        model.addAttribute("account", new Account());
        return "transfer";
    }

    /**
     * Handles GET request for /logout
     * Removes accountnumber from the session
     * @param model
     * @param session
     * @return welcome page
     */
    @GetMapping("/logout")
    public String logout(Model model,HttpSession session) {
        session.invalidate();
        model.asMap().clear();
        session.removeAttribute("account");
        model.addAttribute("account", new Account());
        return "welcome";
      }

    /**
     * Handles POST request for /transfer
     * @param targetAccount the account where money is to be transferred
     * @param session gets current account number from the session
     * @param model adds messages to the view
     * @return transfer HTML filename as a String
     * @author Shipra Behera
     */
    @PostMapping("/transfer")
    public String makeTransfer(@ModelAttribute Account targetAccount, HttpSession session, Model model) {
        //calls the appropriate service function to transfer money
        boolean result = accountService.makeTransfer((Integer)session.getAttribute("accountNum"),
                targetAccount.getAccountNo(), targetAccount.getBalance());
        if (result) {
            model.addAttribute("message", "Transfer was successful");
        } else {
            model.addAttribute("message", "Low balance. Could not transfer. ");
        }
        return "transfer";
    }

    /**
     * Handles POST mapping for /withdraw
     * @param account
     * @param session
     * @param model
     * @return
     */
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

    /**
     * Handles GET request for /deposit
     * @param model
     * @return
     */

    @GetMapping("/deposit")
    public String deposit(Model model) {
        model.addAttribute("account", new Account());
        return "deposit"; //view
    }

    /**
     * Hnadles POST request for /deposit
     * @param account
     * @param session
     * @param model
     * @return
     */
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