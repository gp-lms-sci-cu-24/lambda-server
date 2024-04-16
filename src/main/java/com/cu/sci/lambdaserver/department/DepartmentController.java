package com.cu.sci.lambdaserver.department;


import com.cu.sci.lambdaserver.course.dto.CreateCourseDto;
import com.cu.sci.lambdaserver.department.dto.CreateDepartmentDto;
import com.cu.sci.lambdaserver.department.dto.DepartmentDto;
import com.cu.sci.lambdaserver.department.dto.UpdateDepartmentDto;
import com.cu.sci.lambdaserver.department.services.IDepartmentService;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.utils.enums.Semester;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/v1/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final IDepartmentService departmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDto createDepartment(@RequestBody @Valid CreateDepartmentDto departmentDto) {
        return departmentService.createDepartment(departmentDto) ;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<DepartmentDto> getDepartments(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        return departmentService.getAllDepartments(pageNo, pageSize);
    }

    @GetMapping(path = "/{code}")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentDto getDepartment(@PathVariable("code") String code) {
        return departmentService.getDepartment(code);
    }


    @PatchMapping(path = "/{code}")
    @ResponseStatus(HttpStatus.OK)
    public UpdateDepartmentDto updateDepartment(@PathVariable String code, @RequestBody  UpdateDepartmentDto departmentDto) {
        return departmentService.updateDepartment(code, departmentDto);
    }

    @DeleteMapping(path = "/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepartment(@PathVariable String code) {
        departmentService.deleteDepartment(code);
    }

    @GetMapping(path = "/{code}/courses")
    @ResponseStatus(HttpStatus.OK)
    public Page<CreateCourseDto> getDepartmentCourses(@PathVariable String code, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return departmentService.getDepartmentCourses(code, pageNo, pageSize);
    }


    @GetMapping(path = "/{code}/courses",params = "details=true")
    @ResponseStatus(HttpStatus.OK)
    public Page<CreateCourseDto> getCourseDepartmentbySemster(@PathVariable String code, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam Semester semester) {
        return departmentService.getCourseDepartmentbySemster(code, pageNo, pageSize, semester);
    }

    @GetMapping(path = "/{code}/students")
    @ResponseStatus(HttpStatus.OK)
    public Page<StudentDto> getDepartmentStudents(@PathVariable String code) {
        return departmentService.getDepartmentStudents(code);
    }
}
