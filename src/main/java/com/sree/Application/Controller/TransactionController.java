package com.sree.Application.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sree.Application.Entity.Account;
import com.sree.Application.Entity.Transaction;
import com.sree.Application.Service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Get all transactions for an account
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getAllTransactionsByAccount(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getAllTransactionsByAccount(accountId);
        return ResponseEntity.ok(transactions);
    }

 // Deposit money into an account
    @PostMapping("/deposit")
    public ResponseEntity<Account> deposit(@RequestParam Long accountId, @RequestParam double amount) {
        Account updatedAccount = transactionService.deposit(accountId, amount);
        return ResponseEntity.ok(updatedAccount);
    }

    // Withdraw money from an account
    @PostMapping("/withdraw")
    public ResponseEntity<Account> withdraw(@RequestParam Long accountId, @RequestParam double amount) {
        Account updatedAccount = transactionService.withdraw(accountId, amount);
        return ResponseEntity.ok(updatedAccount);
    }

    // Transfer money between accounts
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam Long fromAccountId, @RequestParam Long toAccountId, @RequestParam double amount) {
        transactionService.transfer(fromAccountId, toAccountId, amount);
        return ResponseEntity.ok("Transfer successful");
    }
}