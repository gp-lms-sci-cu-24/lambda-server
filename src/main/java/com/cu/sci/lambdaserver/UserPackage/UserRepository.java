package com.cu.sci.lambdaserver.UserPackage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User,Integer>{
    Optional<User> findByuserId(String id);

}