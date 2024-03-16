package com.cu.sci.lambdaserver.department.services;

import com.cu.sci.lambdaserver.department.Department;

import java.util.List;

public interface iDepartmentService {
    Department createDepartment (Department department) ;

    List<Department> getAllDepartments() ;

    Department getDepartmentByid(Long id) ;

    Department getDepartmentByname(String name) ;

    Department updateDepartment(Long id) ;

    void deleteDepartment(Long id) ;
}
