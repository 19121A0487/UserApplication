package com.sree.Application.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sree.Application.Entity.User;
import com.sree.Application.DTO.UserDTO;
import com.sree.Application.Repo.UserRepo;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    // Register new user
    public void registerUser(User user) {
        userRepo.save(user);
    }

    // User login logic
    public boolean loginUser(User user) {
        Optional<User> existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return user.getPassword().equals(existingUser.get().getPassword());
        }
        return false;
    }

    // Fetch user by ID and convert to UserDTO
    public UserDTO getUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    // Update user profile and return updated UserDTO
    public UserDTO updateUser(Long id, User updatedUser) {
        User existingUser = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        User savedUser = userRepo.save(existingUser);
        return convertToDTO(savedUser);
    }

    // Delete user by ID
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    // Helper method to convert User entity to UserDTO
    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }
}
