package com.cu.sci.lambdaserver.user.service;

import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.user.UserRepository;
import com.cu.sci.lambdaserver.utils.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

/**
 * UserService class implements IUserService interface.
 * It provides methods for user management in the application.
 */
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    /**
     * UserRepository instance for interacting with the database.
     */
    private final UserRepository userRepository;

    /**
     * Loads a user by their username.
     * Throws UsernameNotFoundException if the user is not found.
     *
     * @param username the username of the user to be loaded.
     * @return a User object.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
    }

    /**
     * Fetches all users from the database.
     *
     * @return a collection of User objects.
     */
    @Override
    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Loads a user by their ID.
     * Throws UsernameNotFoundException if the user is not found.
     *
     * @param id the ID of the user to be loaded.
     * @return a User object.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public User loadUserByID(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User deleteUserByUsername(String username) {
        if (!userRepository.existsByUsernameIgnoreCase(username))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        return userRepository.deleteByUsernameIgnoreCase(username);
    }

    /**
     * Deletes a user by their ID.
     * This method is not yet implemented.
     *
     * @param id the ID of the user to be deleted.
     * @return a boolean indicating the success of the operation.
     */
    @Override
    public boolean deleteUserByID(Long id) {
        if (!userRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public User createUser(User user) {
        if (userRepository.existsByUsernameIgnoreCase(user.getUsername()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        if (!userRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public Collection<User> getUsersByRole(Role role) {
        return userRepository.findAllByRolesContaining(role);
    }


}