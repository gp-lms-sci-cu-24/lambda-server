package com.cu.sci.lambdaserver.department.services;

import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.student.Student;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface iDepartmentService {
    Department createDepartment (Department department) ;

    Page<Department> getAllDepartments(Integer pageNo , Integer pageSize) ;

    Department getDepartmentByid(Long id) ;

    Department getDepartmentByname(String name) ;

    Department updateDepartment(Long id,Department department) ;

    List<Student> getStudentsdepartment(Long id) ;

    void deleteDepartment(Long id) ;
}
