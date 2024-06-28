package com.cu.sci.lambdaserver.department;


import com.cu.sci.lambdaserver.course.dto.DepartmentCoursesCollectingDto;
import com.cu.sci.lambdaserver.department.dto.CreateUpdateDepartmentDto;
import com.cu.sci.lambdaserver.department.dto.DepartmentDto;
import com.cu.sci.lambdaserver.department.services.IDepartmentService;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final IDepartmentService departmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDto createDepartment(@RequestBody @Valid CreateUpdateDepartmentDto departmentDto) {
        return departmentService.createDepartment(departmentDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DepartmentDto> getDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping(path = "/{code}")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentDto getDepartment(@PathVariable("code") String code) {
        return departmentService.getDepartmentByCode(code);
    }


    @PutMapping(path = "/{code}")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentDto updateDepartment(@PathVariable String code, @RequestBody @Valid CreateUpdateDepartmentDto departmentDto) {
        return departmentService.updateDepartmentByCode(code, departmentDto);
    }

    @DeleteMapping(path = "/{code}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse deleteDepartment(@PathVariable String code) {
        return departmentService.deleteDepartmentByCode(code);
    }

    @GetMapping(path = "/{code}/courses")
    @ResponseStatus(HttpStatus.OK)
    public List<DepartmentCoursesCollectingDto> getDepartmentCourses(@PathVariable String code) {
        return departmentService.getDepartmentCoursesByCode(code);
    }


    @GetMapping(path = "/{code}/courses", params = "details=true")
    @ResponseStatus(HttpStatus.OK)
    public List<DepartmentCoursesCollectingDto> getCourseDepartmentBySemester(@PathVariable String code, @RequestParam YearSemester semester) {
        return departmentService.getCourseDepartmentByCodeAndSemester(code, semester);
    }

    @GetMapping(path = "/{code}/students")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentDto> getDepartmentStudents(@PathVariable String code) {
        return departmentService.getDepartmentStudentsByCode(code);
    }
}
