package com.sree.Application.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sree.Application.Entity.Account;
import com.sree.Application.Entity.User;
@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    List<Account> findByUser(User user);
}