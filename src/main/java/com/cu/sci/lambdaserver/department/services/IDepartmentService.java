package com.cu.sci.lambdaserver.department.services;

import com.cu.sci.lambdaserver.course.dto.DepartmentCoursesCollectingDto;
import com.cu.sci.lambdaserver.department.dto.CreateDepartmentDto;
import com.cu.sci.lambdaserver.department.dto.DepartmentDto;
import com.cu.sci.lambdaserver.department.dto.UpdateDepartmentDto;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.Semester;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * This interface provides the contract for the Department Service.
 */
public interface IDepartmentService {

    /**
     * Creates a new department.
     * This method is secured with Spring Security's @PreAuthorize annotation, which restricts access to users with 'ADMIN' role.
     *
     * @param department The department to be created.
     * @return The created department.
     */
    @PreAuthorize("hasRole('ADMIN')")
    DepartmentDto createDepartment(CreateDepartmentDto department);

    /**
     * Updates an existing department.
     * This method is secured with Spring Security's @PreAuthorize annotation, which restricts access to users with 'ADMIN' role.
     *
     * @param code       The code of the department to be updated.
     * @param department The updated department details.
     * @return The updated department.
     */
    @PreAuthorize("hasRole('ADMIN')")
    UpdateDepartmentDto updateDepartmentByCode(String code, UpdateDepartmentDto department);

    /**
     * Deletes a department.
     * This method is secured with Spring Security's @PreAuthorize annotation, which restricts access to users with 'ADMIN' role.
     *
     * @param code The code of the department to be deleted.
     * @return A MessageResponse object containing the result of the deletion operation.
     */
    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse deleteDepartmentByCode(String code);

    /**
     * Retrieves the students of a specific department.
     * This method is secured with Spring Security's @PreAuthorize annotation, which restricts access to users with 'ADMIN' or 'STUDENT_AFFAIR' roles.
     *
     * @param code     The code of the department whose students are to be retrieved.
     * @param pageNo   The page number to retrieve. This is used for pagination purposes.
     * @param pageSize The size of the page to retrieve. This is also used for pagination purposes.
     * @return A Page object containing StudentDto objects. Each StudentDto represents a student in the specified department.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT_AFFAIR')")
    Page<StudentDto> getDepartmentStudentsByCode(String code, Integer pageNo, Integer pageSize);

    /**
     * Retrieves all departments.
     *
     * @param pageNo   The page number to retrieve.
     * @param pageSize The size of the page to retrieve.
     * @return A page of departments.
     */
    Page<DepartmentDto> getAllDepartments(Integer pageNo, Integer pageSize);

    /**
     * Retrieves a department.
     *
     * @param code The code of the department to retrieve.
     * @return The retrieved department.
     */
    DepartmentDto getDepartmentByCode(String code);

    /**
     * Retrieves the courses of a department.
     *
     * @param code     The code of the department.
     * @param pageNo   The page number to retrieve.
     * @param pageSize The size of the page to retrieve.
     * @return A page of courses.
     */
    Page<DepartmentCoursesCollectingDto> getDepartmentCoursesByCode(String code, Integer pageNo, Integer pageSize);

    /**
     * Retrieves the courses of a department for a specific semester.
     *
     * @param code     The code of the department.
     * @param pageNo   The page number to retrieve.
     * @param pageSize The size of the page to retrieve.
     * @param semester The semester to retrieve courses for.
     * @return A page of courses.
     */
    Page<DepartmentCoursesCollectingDto> getCourseDepartmentByCodeAndSemester(String code, Integer pageNo, Integer pageSize, Semester semester);
}