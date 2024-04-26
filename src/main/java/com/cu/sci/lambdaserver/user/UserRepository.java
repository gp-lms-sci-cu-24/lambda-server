package com.cu.sci.lambdaserver.user;

import org.springframework.stereotype.Repository;

/**
 * UserRepository interface extends CrudRepository interface from Spring Data.
 * It provides methods for interacting with the User table in the database.
 */
@Repository
public interface UserRepository extends UserAbstractionRepository<User> {

}