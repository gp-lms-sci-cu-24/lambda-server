package com.cu.sci.lambdaserver.UserPackage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> userOptional = userService.fetchUserById(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.fetchAllUsers();
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users); // Return HTTP 200 OK with users
        } else {
            return ResponseEntity.noContent().build(); // Return HTTP 204 No Content
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        try {
            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                return ResponseEntity.ok().build(); // Return HTTP 200 OK
            } else {
                return ResponseEntity.notFound().build(); // Return HTTP 404 Not Found
            }
        } catch (Exception e) {
            // Log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return HTTP 500 Internal Server Error
        }
    }
    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        try {
            User savedUser = userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser); // Return HTTP 201 Created with the saved user
        } catch (Exception e) {
            // Log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return HTTP 500 Internal Server Error
        }
    }
}
