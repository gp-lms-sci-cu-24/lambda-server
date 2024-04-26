package com.cu.sci.lambdaserver.user;

import com.cu.sci.lambdaserver.user.dto.UserDto;
import com.cu.sci.lambdaserver.user.service.IUserService;
import com.cu.sci.lambdaserver.user.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final ProfileService profileService;

    @GetMapping("/me")
    public UserDto getMyState() {
        return profileService.getMyState();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.loadUserByID(id);
    }

    @GetMapping
    public ResponseEntity<Collection<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteUserById(@PathVariable Long id) {
        return userService.deleteUserByID(id);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public User deleteUserByUsername(@PathVariable String username) {
        return userService.deleteUserByUsername(username);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @GetMapping("/roles/{role}")
    public Collection<User> getAllUsersByRole(@PathVariable Role role) {
        return userService.getUsersByRole(role);
    }
}
