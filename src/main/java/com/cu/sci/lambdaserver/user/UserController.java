package com.cu.sci.lambdaserver.user;

import com.cu.sci.lambdaserver.user.dto.UserDto;
import com.cu.sci.lambdaserver.user.service.IUserService;
import com.cu.sci.lambdaserver.user.service.ProfileService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        return ResponseEntity.internalServerError().body("Not Implemented Yet!");
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) {
        return ResponseEntity.internalServerError().body("Not Implemented Yet!");
    }
}
