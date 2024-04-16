package com.cu.sci.lambdaserver.department.mapper;

import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.department.dto.DepartmentDto;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper implements IMapper<Department, DepartmentDto> {

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

    @Override
    public Department update(DepartmentDto departmentDto, Department department) {
        return null;
    }
}
