package com.cu.sci.lambdaserver.user.service;

import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    /**
     * Deletes a user by their username.
     * This method is not yet implemented.
     *
     * @param username the username of the user to be deleted.
     * @return a boolean indicating the success of the operation.
     */
    @Override
    public boolean deleteUserByUsername(String username) {
        return false;
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
        return false;
    }

}