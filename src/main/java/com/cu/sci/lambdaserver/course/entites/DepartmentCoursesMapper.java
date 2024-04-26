package com.cu.sci.lambdaserver.course.entites;

import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepartmentCoursesMapper implements IMapper<DepartmentCourses, DepartmentCoursesDto> {
    private final ModelMapper modelMapper;

    @Override
    public DepartmentCoursesDto mapTo(DepartmentCourses departmentCourses) {
        return modelMapper.map(departmentCourses, DepartmentCoursesDto.class);
    }

    @Override
    public DepartmentCourses mapFrom(DepartmentCoursesDto departmentCoursesDto) {
        return modelMapper.map(departmentCoursesDto, DepartmentCourses.class);
    }

    @Override
    public DepartmentCourses update(DepartmentCoursesDto departmentCoursesDto, DepartmentCourses departmentCourses) {
        return null;
    }
}
