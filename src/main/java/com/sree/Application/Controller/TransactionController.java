package com.sree.Application.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sree.Application.DTO.AccountDTO;
import com.sree.Application.DTO.TransactionDTO;
import com.sree.Application.Service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Get all transactions for an account
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionDTO>> getAllTransactionsByAccount(@PathVariable Long accountId) {
        List<TransactionDTO> transactions = transactionService.getAllTransactionsByAccount(accountId);
        return ResponseEntity.ok(transactions);
    }

    // Deposit money into an account
    @PostMapping("/deposit")
    public ResponseEntity<AccountDTO> deposit(@RequestParam Long accountId, @RequestParam double amount) {
        AccountDTO updatedAccount = transactionService.deposit(accountId, amount);
        return ResponseEntity.ok(updatedAccount);
    }

    // Withdraw money from an account
    @PostMapping("/withdraw")
    public ResponseEntity<AccountDTO> withdraw(@RequestParam Long accountId, @RequestParam double amount) {
        AccountDTO updatedAccount = transactionService.withdraw(accountId, amount);
        return ResponseEntity.ok(updatedAccount);
    }

    // Transfer money between accounts
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam Long fromAccountId, @RequestParam Long toAccountId, @RequestParam double amount) {
        transactionService.transfer(fromAccountId, toAccountId, amount);
        return ResponseEntity.ok("Transfer successful");
    }
}
