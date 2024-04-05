package com.cu.sci.lambdaserver.department.services;

import com.cu.sci.lambdaserver.course.dto.CreateCourseDto;
import com.cu.sci.lambdaserver.course.entites.DepartmentCourses;
import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.department.dto.DepartmentDto;
import com.cu.sci.lambdaserver.department.dto.UpdateDepartmentDto;
import com.cu.sci.lambdaserver.student.Student;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDepartmentService {
    DepartmentDto createDepartment (DepartmentDto department) ;

    Page<DepartmentDto> getAllDepartments(Integer pageNo , Integer pageSize) ;

    DepartmentDto getDepartment(String code) ;

    UpdateDepartmentDto updateDepartment(String code , UpdateDepartmentDto department) ;

    void deleteDepartment(String code) ;

    Page<CreateCourseDto> getDepartmentCourses(String code , Integer pageNo , Integer pageSize) ;

}
