package com.sree.Application.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sree.Application.DTO.AccountDTO;
import com.sree.Application.Entity.Account;
import com.sree.Application.Service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Convert Account entity to AccountDTO
    private AccountDTO convertToDto(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setAccountType(account.getAccountType());
        dto.setBalance(account.getBalance());
        return dto;
    }

    // Convert AccountDTO to Account entity
    private Account convertToEntity(AccountDTO dto) {
        Account account = new Account();
        account.setAccountType(dto.getAccountType());
        account.setBalance(dto.getBalance());
        return account;
    }

    // Get all accounts for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountDTO>> getAllAccountsByUser(@PathVariable Long userId) {
        List<Account> accounts = accountService.getAllAccountsByUser(userId);
        List<AccountDTO> accountDTOs = accounts.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(accountDTOs);
    }

    // Get account details by account ID
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long accountId) {
        Account account = accountService.getAccountById(accountId);
        return ResponseEntity.ok(convertToDto(account));
    }

    // Create a new account
    @PostMapping("/user/{userId}")
    public ResponseEntity<String> createAccount(@PathVariable Long userId, @RequestBody AccountDTO accountDTO) {
        Account account = convertToEntity(accountDTO);
        accountService.createAccount(userId, account);
        return ResponseEntity.ok("Account created successfully");
    }

    // Update account details
    @PutMapping("/{accountId}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long accountId, @RequestBody AccountDTO accountDTO) {
        Account accountDetails = convertToEntity(accountDTO);
        Account account = accountService.updateAccount(accountId, accountDetails);
        return ResponseEntity.ok(convertToDto(account));
    }

    // Delete account
    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.ok("Account deleted successfully");
    }
}
