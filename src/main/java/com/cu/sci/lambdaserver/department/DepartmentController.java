package com.cu.sci.lambdaserver.department;


import com.cu.sci.lambdaserver.department.services.DepartmentService;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    private final iMapper<Department, DepartmentDto> departmentMapper;

    @PostMapping
    public ResponseEntity createDepartment(DepartmentDto departmentDto){
        Department department = departmentMapper.mapFrom(departmentDto) ;
        departmentService.createDepartment(department) ;
        return new ResponseEntity(HttpStatus.CREATED) ;
    }
}
