package com.cu.sci.lambdaserver.department.mapper;

import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.department.dto.CreateDepartmentDto;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CreateDepartmentMapper implements iMapper<Department, CreateDepartmentDto> {

    private final ModelMapper modelMapper;

    public CreateDepartmentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CreateDepartmentDto mapTo(Department department) {
        return modelMapper.map(department, CreateDepartmentDto.class);
    }

    @Override
    public Department mapFrom(CreateDepartmentDto createDepartmentDto) {
        return modelMapper.map(createDepartmentDto, Department.class);
    }

    @Override
    public Department update(CreateDepartmentDto createDepartmentDto, Department department) {
        return null;
    }
}
