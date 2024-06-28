package com.cu.sci.lambdaserver.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    boolean existsByNameIgnoreCase(String name);

    Optional<Location> findByNameIgnoreCase(String name);
}
