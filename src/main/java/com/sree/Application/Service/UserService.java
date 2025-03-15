package com.sree.Application.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sree.Application.Entity.User;
import com.sree.Application.Repo.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    // Register new user
    public void registerUser(User user) {
        // Save the user directly without password hashing
        userRepo.save(user);
    }

    // User login logic
    public boolean loginUser(User user) {
        Optional<User> existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            // Compare plain-text password (not recommended)
            return user.getPassword().equals(existingUser.get().getPassword());
        }
        return false;
    }

    // Fetch user by ID
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Update user profile
    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());  // Update password as plain text
        return userRepo.save(existingUser);
    }

    // Delete user by ID
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}
