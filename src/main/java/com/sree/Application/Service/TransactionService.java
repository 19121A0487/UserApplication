package com.sree.Application.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sree.Application.Entity.Account;
import com.sree.Application.Entity.Transaction;
import com.sree.Application.Repo.AccountRepo;
import com.sree.Application.Repo.TransactionRepo;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private AccountRepo accountRepo;

    // Get all transactions for a specific account
    public List<Transaction> getAllTransactionsByAccount(Long accountId) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return transactionRepo.findByAccount(account);
    }

    // Deposit money into an account
    public Account deposit(Long accountId, double amount) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Update account balance
        account.setBalance(account.getBalance() + amount);
        accountRepo.save(account);

        // Record the transaction
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType("Deposit");
        transaction.setBalance(account.getBalance()); // Set updated balance
        transactionRepo.save(transaction);

        return account;
    }

    // Withdraw money from an account
    public Account withdraw(Long accountId, double amount) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Check if there are sufficient funds
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        // Update account balance
        account.setBalance(account.getBalance() - amount);
        accountRepo.save(account);

        // Record the transaction
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(-amount); // Negative for withdrawals
        transaction.setType("Withdrawal");
        transaction.setBalance(account.getBalance()); // Set updated balance
        transactionRepo.save(transaction);

        return account;
    }

    // Transfer money between accounts
    public void transfer(Long fromAccountId, Long toAccountId, double amount) {
        Account fromAccount = accountRepo.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("From account not found"));
        Account toAccount = accountRepo.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("To account not found"));

        // Withdraw from sender's account
        withdraw(fromAccountId, amount);

        // Deposit into receiver's account
        deposit(toAccountId, amount);

        // Record the transfer transaction for both accounts
        Transaction fromTransaction = new Transaction();
        fromTransaction.setAccount(fromAccount);
        fromTransaction.setAmount(-amount);
        fromTransaction.setType("Transfer to account " + toAccountId);
        fromTransaction.setBalance(fromAccount.getBalance()); // Set updated balance
        transactionRepo.save(fromTransaction);

        Transaction toTransaction = new Transaction();
        toTransaction.setAccount(toAccount);
        toTransaction.setAmount(amount);
        toTransaction.setType("Transfer from account " + fromAccountId);
        toTransaction.setBalance(toAccount.getBalance()); // Set updated balance
        transactionRepo.save(toTransaction);
    }
}
