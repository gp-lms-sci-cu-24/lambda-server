package com.cu.sci.lambdaserver.department.mapper;

import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.department.dto.CreateUpdateDepartmentDto;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CreateDepartmentMapper implements IMapper<Department, CreateUpdateDepartmentDto> {

    private final ModelMapper modelMapper;

    public CreateDepartmentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CreateUpdateDepartmentDto mapTo(Department department) {
        return modelMapper.map(department, CreateUpdateDepartmentDto.class);
    }

    @Override
    public Department mapFrom(CreateUpdateDepartmentDto createUpdateDepartmentDto) {
        return modelMapper.map(createUpdateDepartmentDto, Department.class);
    }

    @Override
    public Department update(CreateUpdateDepartmentDto createUpdateDepartmentDto, Department department) {
        return null;
    }
}
