package com.cu.sci.lambdaserver.user.service;

import com.cu.sci.lambdaserver.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

/**
 * IUserService interface extends UserDetailsService from Spring Security.
 * It provides methods for user management in the application.
 */
public interface IUserService extends UserDetailsService {

    /**
     * Fetches all users from the database.
     *
     * @return a collection of User objects.
     */
    Collection<User> getAllUsers();

    /**
     * Loads a user by their username.
     * This method is overridden from UserDetailsService.
     *
     * @param username the username of the user to be loaded.
     * @return a User object.
     */
    @Override
    User loadUserByUsername(String username);

    /**
     * Loads a user by their ID.
     *
     * @param id the ID of the user to be loaded.
     * @return a User object.
     */
    User loadUserByID(Long id);

    /**
     * Deletes a user by their username.
     *
     * @param username the username of the user to be deleted.
     * @return a boolean indicating the success of the operation.
     */
    boolean deleteUserByUsername(String username);

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to be deleted.
     * @return a boolean indicating the success of the operation.
     */
    boolean deleteUserByID(Long id);
}