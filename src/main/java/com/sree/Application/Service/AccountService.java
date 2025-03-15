package com.sree.Application.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sree.Application.Entity.Account;
import com.sree.Application.Repo.AccountRepo;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    // Create a new account for a user
    public Account createAccount(Long userId, Account account) {
        // Assuming userId is being handled in your repository logic
        // You might need to set the user ID in the account object here
        // account.setUserId(userId); // Ensure this is in place if needed
        return accountRepo.save(account);
    }

    // Get account by ID
    public Account getAccountById(Long accountId) {
        return accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // Update account details
    public Account updateAccount(Long accountId, Account accountDetails) {
        Account existingAccount = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        existingAccount.setAccountType(accountDetails.getAccountType());
        existingAccount.setBalance(accountDetails.getBalance());

        return accountRepo.save(existingAccount);
    }

    // Delete an account by ID
    public void deleteAccount(Long accountId) {
        Account existingAccount = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        accountRepo.delete(existingAccount);
    }

    // Get all accounts by a user ID
    public List<Account> getAllAccountsByUser(Long userId) {
        // Assuming there's a method in AccountRepo to find accounts by user ID
        return accountRepo.findByUserId(userId);
    }
}
