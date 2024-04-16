package com.cu.sci.lambdaserver.department.services;

import com.cu.sci.lambdaserver.course.dto.CreateCourseDto;
import com.cu.sci.lambdaserver.department.dto.CreateDepartmentDto;
import com.cu.sci.lambdaserver.department.dto.DepartmentDto;
import com.cu.sci.lambdaserver.department.dto.UpdateDepartmentDto;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.utils.enums.Semester;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

public interface IDepartmentService {
    @PreAuthorize("hasRole('ADMIN')")
    DepartmentDto createDepartment(CreateDepartmentDto department);

    @PreAuthorize("hasRole('ADMIN')")
    UpdateDepartmentDto updateDepartment(String code, UpdateDepartmentDto department);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteDepartment(String code);

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT_AFFAIR')")
    Page<StudentDto> getDepartmentStudents(String code);


    Page<DepartmentDto> getAllDepartments(Integer pageNo, Integer pageSize);

    DepartmentDto getDepartment(String code);

    Page<CreateCourseDto> getDepartmentCourses(String code, Integer pageNo, Integer pageSize);

    Page<CreateCourseDto> getCourseDepartmentBySemester(String code, Integer pageNo, Integer pageSize, Semester semester);
}
