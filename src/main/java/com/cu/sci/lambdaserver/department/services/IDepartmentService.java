package com.cu.sci.lambdaserver.department.services;

import com.cu.sci.lambdaserver.course.dto.DepartmentCoursesCollectingDto;
import com.cu.sci.lambdaserver.department.dto.CreateUpdateDepartmentDto;
import com.cu.sci.lambdaserver.department.dto.DepartmentDto;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

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
    DepartmentDto createDepartment(CreateUpdateDepartmentDto department);

    /**
     * Updates an existing department.
     * This method is secured with Spring Security's @PreAuthorize annotation, which restricts access to users with 'ADMIN' role.
     *
     * @param code       The code of the department to be updated.
     * @param department The updated department details.
     * @return The updated department.
     */
    @PreAuthorize("hasRole('ADMIN')")
    DepartmentDto updateDepartmentByCode(String code, CreateUpdateDepartmentDto department);

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
     * @return A List object containing StudentDto objects. Each StudentDto represents a student in the specified department.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT_AFFAIR')")
    List<StudentDto> getDepartmentStudentsByCode(String code);

    /**
     * Retrieves all departments.
     *
     * @return A List of departments.
     */
    List<DepartmentDto> getAllDepartments();

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
     * @return A List of courses.
     */
    List<DepartmentCoursesCollectingDto> getDepartmentCoursesByCode(String code);

    /**
     * Retrieves the courses of a department for a specific semester.
     *
     * @param code     The code of the department.
     * @param semester The semester to retrieve courses for.
     * @return A List of courses.
     */
    List<DepartmentCoursesCollectingDto> getCourseDepartmentByCodeAndSemester(String code, YearSemester semester);
}