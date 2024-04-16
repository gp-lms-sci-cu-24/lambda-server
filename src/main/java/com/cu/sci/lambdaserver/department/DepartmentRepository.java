package com.cu.sci.lambdaserver.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long>, PagingAndSortingRepository<Department, Long> {
    Optional<Department> findDepartmentByNameIgnoreCase(String name);

    Optional<Department> findDepartmentByCodeIgnoreCase(String code);


}
