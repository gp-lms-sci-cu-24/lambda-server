package com.cu.sci.lambdaserver.department;

import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import org.modelmapper.ModelMapper;

@Component
public class DepartmentMapper implements iMapper<Department,DepartmentDto> {

    private final ModelMapper modelMapper;

    public DepartmentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public DepartmentDto mapTo(Department department) {
        return modelMapper.map(department, DepartmentDto.class);
    }

    @Override
    public Department mapFrom(DepartmentDto departmentDto) {
        return modelMapper.map(departmentDto, Department.class);
    }
}
