package com.cu.sci.lambdaserver.department.mapper;

import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.department.dto.UpdateDepartmentDto;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UpdateDepartmentMapper implements IMapper<Department, UpdateDepartmentDto> {
    private final ModelMapper modelMapper;

    public UpdateDepartmentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UpdateDepartmentDto mapTo(Department department) {
        return modelMapper.map(department, UpdateDepartmentDto.class);
    }

    @Override
    public Department mapFrom(UpdateDepartmentDto updateDepartmentDto) {
        return modelMapper.map(updateDepartmentDto, Department.class);
    }

    @Override
    public Department update(UpdateDepartmentDto updateDepartmentDto, Department department) {
        return null;
    }
}
