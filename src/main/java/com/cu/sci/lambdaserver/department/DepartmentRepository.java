package com.cu.sci.lambdaserver.department;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
    Optional<Department> findDepartmentByName(String name) ;
    boolean existsById (Long id) ;
}
