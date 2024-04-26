package com.cu.sci.lambdaserver.academicadvisor.service;

import com.cu.sci.lambdaserver.academicadvisor.AdvisorType;
import com.cu.sci.lambdaserver.academicadvisor.dto.UsernameRequestDto;
import com.cu.sci.lambdaserver.professor.dto.ProfessorDto;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.user.dto.UserDto;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Collection;

/**
 * The IAcademicAdvisorService interface defines the contract for the Academic Advisor service.
 * It contains methods for assigning and removing the academic advisor role, assigning and removing users,
 * retrieving all academic advisors, and retrieving assigned users and students.
 */
public interface IAcademicAdvisorService {

    /**
     * Assigns the academic advisor role to a user.
     *
     * @param usernameDto The UsernameRequestDto object containing the username of the user.
     * @return A MessageResponse object containing the result of the operation.
     */
    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse assignAcademicAdvisorRole(UsernameRequestDto usernameDto);

    /**
     * Removes the academic advisor role from a user.
     *
     * @param usernameDto The UsernameRequestDto object containing the username of the user.
     * @return A MessageResponse object containing the result of the operation.
     */
    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse removeAcademicAdvisorRole(UsernameRequestDto usernameDto);

    /**
     * Retrieves all academic advisors.
     *
     * @param pageNo   The page number to retrieve. This is used for pagination purposes.
     * @param pageSize The size of the page to retrieve. This is also used for pagination purposes.
     * @return A Page object containing UserDto objects. Each UserDto represents an academic advisor.
     */
    @PreAuthorize("hasRole('ADMIN')")
    Page<UserDto> getAllAcademicAdvisors(Integer pageNo, Integer pageSize);

    /**
     * Assigns a user to a professor.
     *
     * @param professorUsername The username of the professor.
     * @param usernameDto       The UsernameRequestDto object containing the username of the user.
     * @return A MessageResponse object containing the result of the operation.
     */
    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse assignUser(String professorUsername, UsernameRequestDto usernameDto);

    /**
     * Removes a user from a professor.
     *
     * @param professorUsername The username of the professor.
     * @param usernameDto       The UsernameRequestDto object containing the username of the user.
     * @return A MessageResponse object containing the result of the operation.
     */
    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse removeUser(String professorUsername, UsernameRequestDto usernameDto);

    /**
     * Retrieves the users assigned to a professor.
     *
     * @param professorUsername The username of the professor.
     * @param pageNo            The page number to retrieve. This is used for pagination purposes.
     * @param pageSize          The size of the page to retrieve. This is also used for pagination purposes.
     * @return A Page object containing UserDto objects. Each UserDto represents a user assigned to the professor.
     */
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
    Page<UserDto> getAssignedUsers(String professorUsername, Integer pageNo, Integer pageSize);

    /**
     * Retrieves the users assigned to a professor by type.
     *
     * @param professorUsername The username of the professor.
     * @param type              The type of advisor.
     * @param pageNo            The page number to retrieve. This is used for pagination purposes.
     * @param pageSize          The size of the page to retrieve. This is also used for pagination purposes.
     * @return A Page object containing UserDto objects. Each UserDto represents a user assigned to the professor of the specified type.
     */
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
    Page<UserDto> getAssignedUsersByType(String professorUsername, AdvisorType type, Integer pageNo, Integer pageSize);

    /**
     * Retrieves the students assigned to a professor, nested by level.
     *
     * @param professorUsername The username of the professor.
     * @param nestingLevel      The level of nesting.
     * @return A Collection of StudentDto objects. Each StudentDto represents a student assigned to the professor, nested by the specified level.
     */
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
    Collection<StudentDto> getNestedAssignedStudents(String professorUsername, Integer nestingLevel);

    /**
     * Retrieves the academic advisor of the currently logged-in user.
     *
     * @return A ProfessorDto object representing the academic advisor of the currently logged-in user.
     */
    @PreAuthorize("hasAnyRole('STUDENT','ACADEMIC_ADVISOR')")
    ProfessorDto getMyAcademicAdvisor();
}