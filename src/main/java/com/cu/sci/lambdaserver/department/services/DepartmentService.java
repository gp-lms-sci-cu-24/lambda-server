package com.cu.sci.lambdaserver.department.services;

import com.cu.sci.lambdaserver.course.dto.DepartmentCoursesCollectingDto;
import com.cu.sci.lambdaserver.course.entites.DepartmentCourses;
import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.department.DepartmentRepository;
import com.cu.sci.lambdaserver.department.dto.CreateDepartmentDto;
import com.cu.sci.lambdaserver.department.dto.DepartmentDto;
import com.cu.sci.lambdaserver.department.dto.UpdateDepartmentDto;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.Semester;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The DepartmentService class provides the business logic for the Department entity.
 * It implements the IDepartmentService interface which defines the methods that this service class must implement.
 * This class is annotated with @Service to indicate that it's a service component in the Spring framework.
 * The @RequiredArgsConstructor annotation is a Lombok annotation that generates a constructor with required fields.
 * Required fields are final fields and fields with constraints such as @NonNull.
 */
@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService {

    private final DepartmentRepository departmentRepository;
    private final IMapper<Department, DepartmentDto> departmentMapper;
    private final IMapper<Department, CreateDepartmentDto> createDepartmentDtoiMapper;
    private final IMapper<Department, UpdateDepartmentDto> updateDepartmentDtoiMapper;
    private final IMapper<Student, StudentDto> studentDtoiMapper;
    private final IMapper<DepartmentCourses, DepartmentCoursesCollectingDto> departmentCoursesCollectingMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public DepartmentDto createDepartment(CreateDepartmentDto department) {
        // check if department is already exist
        Boolean foundedDepartmentByCode = departmentRepository
                .existsByCodeIgnoreCase(department.getCode());
        Boolean foundedDepartmentByName = departmentRepository
                .existsByNameIgnoreCase(department.getName());

        if (foundedDepartmentByCode) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Department code is already exist.");
        }
        if (foundedDepartmentByName) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Department name is already exist.");
        }

        // convert dto to entity and save it to db
        Department savedDepartment = createDepartmentDtoiMapper.mapFrom(department);
        return departmentMapper.mapTo(departmentRepository.save(savedDepartment));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<DepartmentDto> getAllDepartments(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Department> departmentPage = departmentRepository.findAll(pageable);
        //check if list empty
        if (departmentPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return departmentPage.map(departmentMapper::mapTo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DepartmentDto getDepartmentByCode(String code) {
        Optional<Department> department = departmentRepository
                .findDepartmentByCodeIgnoreCase(code);
        // check if department exist
        if (department.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found with this code");
        }
        return departmentMapper.mapTo(department.get());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public UpdateDepartmentDto updateDepartmentByCode(String code, UpdateDepartmentDto departmentDto) {
        // check if department found
        Optional<Department> foundedDepartment = departmentRepository
                .findDepartmentByCodeIgnoreCase(code);
        if (foundedDepartment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " department not found with this code ");
        }

        // check update values if they equal old values
        if (Objects.equals(foundedDepartment.get().getCode(), departmentDto.getCode()) ||
                Objects.equals(foundedDepartment.get().getName(), departmentDto.getName())
        ) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You can't use old code or name.");
        }


        // update department and save it
        foundedDepartment.map(department -> {
            Optional.ofNullable(departmentDto.getName()).ifPresent(department::setName);
            Optional.ofNullable(departmentDto.getInfo()).ifPresent(department::setInfo);
            Optional.ofNullable(departmentDto.getCode()).ifPresent(department::setCode);
            return departmentRepository.save(department);
        });

        // convert saved department to dto
        return updateDepartmentDtoiMapper.mapTo(foundedDepartment.get());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public MessageResponse deleteDepartmentByCode(String code) {
        // check if department found
        Optional<Department> foundedDepartment = departmentRepository
                .findDepartmentByCodeIgnoreCase(code);
        if (foundedDepartment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " department not found with this code ");
        }
        // delete department by code
        departmentRepository.delete(foundedDepartment.get());

        return new MessageResponse("Department with code " + code + " deleted successfully.");
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Page<DepartmentCoursesCollectingDto> getDepartmentCoursesByCode(String code, Integer pageNo, Integer pageSize) {
        // check if department found
        Optional<Department> foundedDepartment = departmentRepository
                .findDepartmentByCodeIgnoreCase(code);
        if (foundedDepartment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found Department with this code.");
        }

        /* @TODO: Fight with person who write this*/
        // get courses of department
        List<DepartmentCoursesCollectingDto> courseList = foundedDepartment.get().getDepartmentCourses().stream()
                .map(departmentCoursesCollectingMapper::mapTo)
                .toList();

        // convert list to page
        int start = (int) PageRequest.of(pageNo, pageSize).getOffset();
        int end = Math.min((start + PageRequest.of(pageNo, pageSize).getPageSize()), courseList.size());

        return new PageImpl<>(courseList.subList(start, end), PageRequest.of(pageNo, pageSize), courseList.size());

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Page<DepartmentCoursesCollectingDto> getCourseDepartmentByCodeAndSemester(String code, Integer pageNo, Integer pageSize, Semester semester) {
        // check if department found
        Optional<Department> foundedDepartment = departmentRepository.findDepartmentByCodeIgnoreCase(code);
        if (foundedDepartment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " department not found with this code ");
        }

        /* @TODO: Fight with person who write this*/
        // get courses of department by semester
        List<DepartmentCoursesCollectingDto> courseList = foundedDepartment.get().getDepartmentCourses().stream()
                .filter(departmentCourses -> departmentCourses.getSemester().equals(semester))
                .map(departmentCoursesCollectingMapper::mapTo).toList();

        // convert list to page
        int start = (int) PageRequest.of(pageNo, pageSize).getOffset();
        int end = Math.min((start + PageRequest.of(pageNo, pageSize).getPageSize()), courseList.size());

        return new PageImpl<>(courseList.subList(start, end), PageRequest.of(pageNo, pageSize), courseList.size());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<StudentDto> getDepartmentStudentsByCode(String code, Integer pageNo, Integer pageSize) {
        /* @TODO: USE Pageable */

        //check if department found
        Optional<Department> foundedDepartment = departmentRepository.findDepartmentByCodeIgnoreCase(code);
        if (foundedDepartment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " department not found with this code ");
        }

        // get students of department
        List<StudentDto> StudentDtoList = foundedDepartment.get()
                .getStudents()
                .stream()
                .map(studentDtoiMapper::mapTo)
                .toList();

        if (StudentDtoList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No students found in this department");
        }

        // convert list to page
        int start = (int) PageRequest.of(pageNo, pageSize).getOffset();
        int end = Math.min((start + PageRequest.of(pageNo, pageSize).getPageSize()), StudentDtoList.size());
        return new PageImpl<>(StudentDtoList.subList(start, end), PageRequest.of(pageNo, pageSize), StudentDtoList.size());
    }
}
