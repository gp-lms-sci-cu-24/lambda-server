package com.cu.sci.lambdaserver.department.services;

import com.cu.sci.lambdaserver.course.dto.CreateCourseDto;
import com.cu.sci.lambdaserver.department.dto.CreateDepartmentDto;
import com.cu.sci.lambdaserver.department.dto.DepartmentDto;
import com.cu.sci.lambdaserver.department.dto.UpdateDepartmentDto;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.utils.enums.Semester;
import org.springframework.data.domain.Page;

public interface IDepartmentService {
    DepartmentDto createDepartment(CreateDepartmentDto department);

    Page<DepartmentDto> getAllDepartments(Integer pageNo, Integer pageSize);

    DepartmentDto getDepartment(String code);

    UpdateDepartmentDto updateDepartment(String code, UpdateDepartmentDto department);

    void deleteDepartment(String code);

    Page<CreateCourseDto> getDepartmentCourses(String code, Integer pageNo, Integer pageSize);

    Page<CreateCourseDto> getCourseDepartmentBySemester(String code, Integer pageNo, Integer pageSize, Semester semester);

    Page<StudentDto> getDepartmentStudents(String code);

}
