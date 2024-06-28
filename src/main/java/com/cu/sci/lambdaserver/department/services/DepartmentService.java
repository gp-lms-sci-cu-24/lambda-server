package com.cu.sci.lambdaserver.department.services;

import com.cu.sci.lambdaserver.course.dto.DepartmentCoursesCollectingDto;
import com.cu.sci.lambdaserver.course.entites.DepartmentCourses;
import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.department.DepartmentRepository;
import com.cu.sci.lambdaserver.department.dto.CreateUpdateDepartmentDto;
import com.cu.sci.lambdaserver.department.dto.DepartmentDto;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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
    private final IMapper<Department, CreateUpdateDepartmentDto> createDepartmentDtoiMapper;
    private final IMapper<Student, StudentDto> studentDtoiMapper;
    private final IMapper<DepartmentCourses, DepartmentCoursesCollectingDto> departmentCoursesCollectingMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public DepartmentDto createDepartment(CreateUpdateDepartmentDto department) {
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
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        //check if list empty
        if (departments.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return departments.stream().map(departmentMapper::mapTo).toList();
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
    public DepartmentDto updateDepartmentByCode(String code, CreateUpdateDepartmentDto dto) {
        // check if department found
        Optional<Department> foundedDepartment = departmentRepository
                .findDepartmentByCodeIgnoreCase(code);
        if (foundedDepartment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found with this code.");
        }
        Department department = foundedDepartment.get();

        if (!department.getName().equalsIgnoreCase(dto.getName())) {
            Boolean foundedDepartmentByName = departmentRepository
                    .existsByNameIgnoreCase(dto.getName());
            if (foundedDepartmentByName) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Department name is already exist.");
            }
        }
        if (!department.getCode().equalsIgnoreCase(dto.getCode())) {
            Boolean foundedDepartmentByCode = departmentRepository
                    .existsByCodeIgnoreCase(dto.getCode());
            if (foundedDepartmentByCode) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Department code is already exist.");
            }
        }


        department.setCode(dto.getCode());
        department.setName(dto.getName());
        department.setInfo(dto.getInfo());
        department.setGraduationCreditHours(dto.getGraduationCreditHours());

        Department updated = departmentRepository.save(department);
        return departmentMapper.mapTo(updated);
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
    public List<DepartmentCoursesCollectingDto> getDepartmentCoursesByCode(String code) {
        // check if department found
        Optional<Department> foundedDepartment = departmentRepository
                .findDepartmentByCodeIgnoreCase(code);
        if (foundedDepartment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found Department with this code.");
        }

        // get courses of department
        List<DepartmentCoursesCollectingDto> courseList = foundedDepartment.get().getDepartmentCourses().stream()
                .map(departmentCoursesCollectingMapper::mapTo)
                .toList();

        return courseList;

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<DepartmentCoursesCollectingDto> getCourseDepartmentByCodeAndSemester(String code, YearSemester semester) {
        // check if department found
        Optional<Department> foundedDepartment = departmentRepository.findDepartmentByCodeIgnoreCase(code);
        if (foundedDepartment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " department not found with this code ");
        }

        // get courses of department by semester
        List<DepartmentCoursesCollectingDto> courseList = foundedDepartment.get().getDepartmentCourses().stream()
                .filter(departmentCourses -> departmentCourses.getSemester().equals(semester))
                .map(departmentCoursesCollectingMapper::mapTo).toList();

        return courseList;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudentDto> getDepartmentStudentsByCode(String code) {
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

        return StudentDtoList;
    }
}
