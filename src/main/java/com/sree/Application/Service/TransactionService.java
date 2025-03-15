package com.sree.Application.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sree.Application.DTO.TransactionDTO;
import com.sree.Application.DTO.AccountDTO;
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

    // Create a transaction between accounts
    public void transfer(Long fromAccountId, Long toAccountId, Double amount) {
        Account fromAccount = accountRepo.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("From account not found"));

        Account toAccount = accountRepo.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("To account not found"));

        if (fromAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds in from account");
        }

        // Deduct amount from the from account
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        accountRepo.save(fromAccount);

        // Create and save transfer out transaction for the from account
        Transaction fromTransaction = new Transaction();
        fromTransaction.setAccount(fromAccount);
        fromTransaction.setAmount(amount);
        fromTransaction.setType("TRANSFER OUT");
        fromTransaction.setBalance(fromAccount.getBalance());
        transactionRepo.save(fromTransaction);

        // Add amount to the to account
        toAccount.setBalance(toAccount.getBalance() + amount);
        accountRepo.save(toAccount);

        // Create and save transfer in transaction for the to account
        Transaction toTransaction = new Transaction();
        toTransaction.setAccount(toAccount);
        toTransaction.setAmount(amount);
        toTransaction.setType("TRANSFER IN");
        toTransaction.setBalance(toAccount.getBalance());
        transactionRepo.save(toTransaction);
    }

    // Get all transactions for a specific account
    public List<TransactionDTO> getAllTransactionsByAccount(Long accountId) {
        return transactionRepo.findByAccountId(accountId)
                .stream().map(this::convertToDTO).toList();
    }

    // Convert Transaction to TransactionDTO
    private TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setType(transaction.getType());
        dto.setBalance(transaction.getBalance());
        return dto;
    }

 // Deposit method
    public AccountDTO deposit(Long accountId, double amount) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount);
        accountRepo.save(account);

        // Save transaction
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType("DEPOSIT");
        transaction.setBalance(account.getBalance());
        transactionRepo.save(transaction);

        // Return updated account info as AccountDTO
        return new AccountDTO(account.getId(), account.getAccountType(), account.getBalance());
    }

    // Withdraw method
    public AccountDTO withdraw(Long accountId, double amount) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepo.save(account);

        // Save transaction
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType("WITHDRAW");
        transaction.setBalance(account.getBalance());
        transactionRepo.save(transaction);

        // Return updated account info as AccountDTO
        return new AccountDTO(account.getId(), account.getAccountType(), account.getBalance());
    }

}
