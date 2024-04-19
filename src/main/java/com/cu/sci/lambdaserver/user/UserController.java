package com.cu.sci.lambdaserver.user;

import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.user.service.IUserService;
import com.cu.sci.lambdaserver.utils.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final IAuthenticationFacade authenticationFacade;

    @GetMapping("/me")
    public Object getMyState() {
        User user = authenticationFacade.getAuthenticatedUser();
        if (user.getRoles().contains(Role.STUDENT)) {
            // user to student


            return user;
        } else if (user.getRoles().contains(Role.PROFESSOR)) {

        } else if (user.getRoles().contains(Role.ADMIN)) {

        }

        return user;
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
