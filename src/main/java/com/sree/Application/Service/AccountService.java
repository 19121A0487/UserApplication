package com.sree.Application.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sree.Application.Entity.Account;
import com.sree.Application.Entity.User;
import com.sree.Application.Repo.AccountRepo;
import com.sree.Application.Repo.UserRepo;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private UserRepo userRepo;

    // Get all accounts for a specific user
    public List<Account> getAllAccountsByUser(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return accountRepo.findByUser(user);
    }

    // Get account details by account ID
    public Account getAccountById(Long accountId) {
        return accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // Create a new account for a user
    public void createAccount(Long userId, Account account) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        account.setUser(user);
        accountRepo.save(account);
    }

    // Update an existing account
    public Account updateAccount(Long accountId, Account updatedAccount) {
        Account existingAccount = getAccountById(accountId);
        existingAccount.setAccountType(updatedAccount.getAccountType());
        existingAccount.setBalance(updatedAccount.getBalance());
        return accountRepo.save(existingAccount);
    }

    // Delete an account by its ID
    public void deleteAccount(Long accountId) {
        accountRepo.deleteById(accountId);
    }
}
