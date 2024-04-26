package com.cu.sci.lambdaserver.user;

import com.cu.sci.lambdaserver.utils.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * This is a generic repository interface for User entities.
 * It extends the CrudRepository interface provided by Spring Data JPA, which provides methods for CRUD operations.
 * The @NoRepositoryBean annotation is used to indicate that this interface should not be treated as a repository bean by Spring.
 * This is typically used when we want to provide common methods to multiple repository interfaces.
 * The generic type T extends User, which means this repository can be used with any type that extends the User class.
 */
@NoRepositoryBean
public interface UserAbstractionRepository<T extends User> extends JpaRepository<T, Long>, PagingAndSortingRepository<T, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to be found.
     * @return an Optional containing the User object if found, or an empty Optional if not found.
     */
    Optional<T> findByUsernameIgnoreCase(String username);


    /**
     * check if  a user exist by their username.
     *
     * @param username the username of the user to be found.
     * @return a boolean to represent if User found.
     */
    boolean existsByUsernameIgnoreCase(String username);

    /**
     * Finds a user by their roles.
     *
     * @param role the roles of the user to be found.
     * @return a collection of User objects with the specified roles.
     */
    Page<T> findByRolesContains(Role role, Pageable pageable);
}
