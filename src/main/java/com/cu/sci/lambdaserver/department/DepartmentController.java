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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public Page<DepartmentDto> getDepartments(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "20") Integer pageSize) {
        return departmentService.getAllDepartments(pageNo, pageSize);
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
    public Page<DepartmentCoursesCollectingDto> getDepartmentCourses(@PathVariable String code, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return departmentService.getDepartmentCoursesByCode(code, pageNo, pageSize);
    }


    @GetMapping(path = "/{code}/courses", params = "details=true")
    @ResponseStatus(HttpStatus.OK)
    public Page<DepartmentCoursesCollectingDto> getCourseDepartmentBySemester(@PathVariable String code, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam YearSemester semester) {
        return departmentService.getCourseDepartmentByCodeAndSemester(code, pageNo, pageSize, semester);
    }

    @GetMapping(path = "/{code}/students")
    @ResponseStatus(HttpStatus.OK)
    public Page<StudentDto> getDepartmentStudents(@PathVariable String code, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return departmentService.getDepartmentStudentsByCode(code, pageNo, pageSize);
    }
}
