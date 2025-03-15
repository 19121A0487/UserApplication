package com.sree.Application.DTO;

import java.util.List;

public class AccountDTO {
    private Long id;
    private String accountType; // Renamed from 'name' to 'accountType'
    private double balance;
    private List<TransactionDTO> transactions;

    // Default constructor
    public AccountDTO() {
    }

    // Constructor with id, accountType, and balance
    public AccountDTO(Long id, String accountType, double balance) {
        this.id = id;
        this.accountType = accountType;
        this.balance = balance;
    }

    // Constructor with all fields
    public AccountDTO(Long id, String accountType, double balance, List<TransactionDTO> transactions) {
        this.id = id;
        this.accountType = accountType;
        this.balance = balance;
        this.transactions = transactions;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }
}
