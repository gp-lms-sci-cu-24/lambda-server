package com.cu.sci.lambdaserver.user;

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
            throw new RuntimeException("Failed to save user: " + e.getMessage());
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
//        User user1 = new User("user1", "password1", "John", "Doe", "john.doe@example.com",
//                "1234567890", "1234567890123", "https://example.com/profile1.jpg", 30, Role.USER);
//
//        User user2 = new User("user2", "password2", "Jane", "Smith", "jane.smith@example.com",
//                "9876543210", "9876543210987", "https://example.com/profile2.jpg", 25, Role.USER);
//
//        User user3 = new User("user3", "password3", "Alice", "Johnson", "alice.johnson@example.com",
//                "5555555555", "5555555555555", "https://example.com/profile3.jpg", 35, Role.USER);
//
//        User user4 = new User("user4", "password4", "Bob", "Brown", "bob.brown@example.com",
//                "9999999999", "9999999999999", "https://example.com/profile4.jpg", 40, Role.USER);
//        userRepository.save(user1);
//        userRepository.save(user2);
//        userRepository.save(user3);
//        userRepository.save(user4);
    }
}