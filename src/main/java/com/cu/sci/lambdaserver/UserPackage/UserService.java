package com.cu.sci.lambdaserver.UserPackage;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user){
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            // Handle exception or log the error
            throw new RuntimeException("Failed to save product: " + e.getMessage());
        }
    }
    public List<User> fetchAllUsers() {
        try {
            return (List<User>) userRepository.findAll();
        } catch (Exception e) {
            // Handle exception or log the error
            throw new RuntimeException("Failed to fetch all users: " + e.getMessage());
        }
    }

    public Optional<User> fetchUserById(String id) {
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            // Handle exception or log the error
            throw new RuntimeException("Failed to fetch user by ID: " + e.getMessage());
        }
    }
    public boolean deleteUser(String id) {
        try {
            userRepository.deleteById(id);
            return true; // Deletion successful
        } catch (Exception e) {
            // Handle exception or log the error
            throw new RuntimeException("Failed to delete user: " + e.getMessage());
        }
    }
    @PostConstruct
    public void seedUsers() {
        User user1 = new User("user1", "password1", "User", "One", "user1@example.com",
                "123456789", "https://example.com/user1.jpg", 25, Role.USER);

        User user2 = new User("user2", "password2", "User", "Two", "user2@example.com",
                "987654321", "https://example.com/user2.jpg", 30, Role.USER);

        User user3 = new User("user3", "password3", "User", "Three", "user3@example.com",
                "456789123", "https://example.com/user3.jpg", 35, Role.USER);
        User user4 = new User("user4", "password4", "User", "Four", "user4@example.com",
                "789123456", "https://example.com/user4.jpg", 40, Role.ADMIN);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
    }
}
