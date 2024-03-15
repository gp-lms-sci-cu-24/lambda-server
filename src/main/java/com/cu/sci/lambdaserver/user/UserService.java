package com.cu.sci.lambdaserver.user;

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
//    @PostConstruct
//    public void seedUsers() {
//        User user1 = new User("user1", "User One");
//        User user2 = new User("user2", "User Two");
//        User user3 = new User("user3", "User Three");
//
//        userRepository.save(user1);
//        userRepository.save(user2);
//        userRepository.save(user3);
//    }
}
