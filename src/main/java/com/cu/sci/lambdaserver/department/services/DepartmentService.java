package com.cu.sci.lambdaserver.department.services;

import com.cu.sci.lambdaserver.course.dto.CreateCourseDto;
import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.department.DepartmentRepository;
import com.cu.sci.lambdaserver.department.dto.CreateDepartmentDto;
import com.cu.sci.lambdaserver.department.dto.DepartmentDto;
import com.cu.sci.lambdaserver.department.dto.UpdateDepartmentDto;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService {

    private final DepartmentRepository departmentRepository;
    private final IMapper<Department, DepartmentDto> departmentMapper;
    private final IMapper<Department, CreateDepartmentDto> createDepartmentDtoiMapper;
    private final IMapper<Department, UpdateDepartmentDto> updateDepartmentDtoiMapper;
    private final IMapper<Student, StudentDto> studentDtoiMapper;

    @Override
    public DepartmentDto createDepartment(CreateDepartmentDto department) {
        // check if department is already exist
        Optional<Department> foundedDepartmentByCode = departmentRepository
                .findDepartmentByCodeIgnoreCase(department.getCode());
        Optional<Department> foundedDepartmentByName = departmentRepository
                .findDepartmentByNameIgnoreCase(department.getName());
        if (foundedDepartmentByCode.isPresent() || foundedDepartmentByName.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Department is already exist.");
        }

        // convert dto to entity and save it to db
        Department savedDepartment = createDepartmentDtoiMapper.mapFrom(department);
        return departmentMapper.mapTo(departmentRepository.save(savedDepartment));

    }

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

    @Override
    public DepartmentDto getDepartment(String code) {
        Optional<Department> department = departmentRepository
                .findDepartmentByCodeIgnoreCase(code);
        // check if department exist
        if (department.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found with this code");
        }
        return departmentMapper.mapTo(department.get());
    }


    @Override
    public UpdateDepartmentDto updateDepartment(String code, UpdateDepartmentDto departmentDto) {
        // check if department found
        Optional<Department> foundedDepartment = departmentRepository
                .findDepartmentByCodeIgnoreCase(code);
        if (foundedDepartment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " department not found with this code ");
        }

        // check update values
        if (Objects.equals(foundedDepartment.get().getCode(), departmentDto.getCode()) || Objects.equals(foundedDepartment.get().getName(), departmentDto.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Code or Name is used");
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


    @Override
    public void deleteDepartment(String code) {
        // check if department found
        Optional<Department> foundedDepartment = departmentRepository
                .findDepartmentByCodeIgnoreCase(code);
        if (foundedDepartment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " department not found with this code ");
        }
        // delete department by code
        departmentRepository.delete(foundedDepartment.get());
    }


    @Override
    public Page<CreateCourseDto> getDepartmentCourses(String code, Integer pageNo, Integer pageSize) {
        // check if department found
        Optional<Department> foundedDepartment = departmentRepository
                .findDepartmentByCodeIgnoreCase(code);
        if (foundedDepartment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " department not found with this code ");
        }

        // get courses of department
        List<CreateCourseDto> courseDtoList = foundedDepartment.get().getDepartmentCourses().stream().map(departmentCourses -> {
            CreateCourseDto createCourseDto = new CreateCourseDto();
            createCourseDto.setCode(departmentCourses.getCourse().getCode());
            createCourseDto.setName(departmentCourses.getCourse().getName());
            createCourseDto.setInfo(departmentCourses.getCourse().getInfo());
            createCourseDto.setCreditHours(departmentCourses.getCourse().getCreditHours());
            createCourseDto.setSemester(departmentCourses.getSemester());
            createCourseDto.setMandatory(departmentCourses.getMandatory());
            createCourseDto.setDepartmentCode(departmentCourses.getDepartment().getDepartmentCourses().stream().map(departmentCourses1 -> departmentCourses1.getDepartment().getCode()).collect(Collectors.toSet()));
            return createCourseDto;
        }).toList();

        // convert list to page
        int start = (int) PageRequest.of(pageNo, pageSize).getOffset();
        int end = Math.min((start + PageRequest.of(pageNo, pageSize).getPageSize()), courseDtoList.size());

        return new PageImpl<>(courseDtoList.subList(start, end), PageRequest.of(pageNo, pageSize), courseDtoList.size());

    }


    @Override
    public Page<CreateCourseDto> getCourseDepartmentBySemester(String code, Integer pageNo, Integer pageSize, Semester semester) {
        // check if department found
        Optional<Department> foundedDepartment = departmentRepository.findDepartmentByCodeIgnoreCase(code);
        if (foundedDepartment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " department not found with this code ");
        }

        // get courses of department by semester
        List<CreateCourseDto> courseDtoList = foundedDepartment.get().getDepartmentCourses().stream()
                .filter(departmentCourses -> departmentCourses.getSemester().equals(semester))
                .map(departmentCourses -> {
                    CreateCourseDto createCourseDto = new CreateCourseDto();
                    createCourseDto.setCode(departmentCourses.getCourse().getCode());
                    createCourseDto.setName(departmentCourses.getCourse().getName());
                    createCourseDto.setInfo(departmentCourses.getCourse().getInfo());
                    createCourseDto.setCreditHours(departmentCourses.getCourse().getCreditHours());
                    createCourseDto.setSemester(departmentCourses.getSemester());
                    createCourseDto.setMandatory(departmentCourses.getMandatory());
                    createCourseDto.setDepartmentCode(departmentCourses.getDepartment().getDepartmentCourses().stream().map(departmentCourses1 -> departmentCourses1.getDepartment().getCode()).collect(Collectors.toSet()));
                    return createCourseDto;
                }).toList();

        // convert list to page
        int start = (int) PageRequest.of(pageNo, pageSize).getOffset();
        int end = Math.min((start + PageRequest.of(pageNo, pageSize).getPageSize()), courseDtoList.size());

        return new PageImpl<>(courseDtoList.subList(start, end), PageRequest.of(pageNo, pageSize), courseDtoList.size());

    }

    @Override
    public Page<StudentDto> getDepartmentStudents(String code) {
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
        return new PageImpl<>(StudentDtoList, PageRequest.of(0, StudentDtoList.size()), StudentDtoList.size());
    }


}
