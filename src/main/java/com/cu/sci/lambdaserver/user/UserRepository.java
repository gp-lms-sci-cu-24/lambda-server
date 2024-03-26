package com.cu.sci.lambdaserver.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository interface extends CrudRepository interface from Spring Data.
 * It provides methods for interacting with the User table in the database.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to be found.
     * @return an Optional containing the User object if found, or an empty Optional if not found.
     */
    Optional<User> findByUsernameIgnoreCase(String username);
}