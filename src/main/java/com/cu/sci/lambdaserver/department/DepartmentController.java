package com.cu.sci.lambdaserver.department;


import com.cu.sci.lambdaserver.department.services.DepartmentService;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    private final iMapper<Department, DepartmentDto> departmentMapper;

    @PostMapping
    public ResponseEntity createDepartment(@RequestBody DepartmentDto departmentDto){
        Department department = departmentMapper.mapFrom(departmentDto) ;
        departmentService.createDepartment(department) ;
        return new ResponseEntity(HttpStatus.CREATED) ;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getDepartments(){
        List<Department> departmentsList = departmentService.getAllDepartments() ;
        List<DepartmentDto>departmentDtos = departmentsList.stream().map(departmentMapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(departmentDtos,HttpStatus.OK) ;
    }
}
