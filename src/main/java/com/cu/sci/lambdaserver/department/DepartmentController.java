package com.cu.sci.lambdaserver.department;


import com.cu.sci.lambdaserver.department.services.DepartmentService;
import com.cu.sci.lambdaserver.department.services.IDepartmentService;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.student.mapper.StudentMapper;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final IDepartmentService departmentService;
    private final iMapper<Department, DepartmentDto> departmentMapper;
    private final StudentMapper studentMapper;

    @PostMapping
    public ResponseEntity createDepartment(@RequestBody DepartmentDto departmentDto) {
        Department department = departmentMapper.mapFrom(departmentDto);
        departmentService.createDepartment(department);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentDto>> getDepartments(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        Page<Department> departmentPage = departmentService.getAllDepartments(pageNo, pageSize);
        Page<DepartmentDto> dtoPage = departmentPage.map(departmentMapper::mapTo);
        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity getDepartment(@PathVariable("id") long id) {
        try {
            Department department = departmentService.getDepartmentByid(id);
            return new ResponseEntity<>(departmentMapper.mapTo(department), HttpStatus.FOUND);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/{id}/students")
    public ResponseEntity getStudentsofDepartment(@PathVariable("id") Long id) {
        try {
            List<StudentDto> students = departmentService.getStudentsdepartment(id).stream().map(studentMapper::mapTo).collect(Collectors.toList());
            return new ResponseEntity(students, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity updateDepartment(@PathVariable Long id, @RequestBody DepartmentDto departmentDto) {
        try {
            Department department = departmentMapper.mapFrom(departmentDto);
            Department updatedDepartment = departmentService.updateDepartment(id, department);
            return new ResponseEntity(departmentMapper.mapTo(updatedDepartment), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteDepartment(@PathVariable Long id) {
        try {
            departmentService.deleteDepartment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
