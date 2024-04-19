package com.cu.sci.lambdaserver.course.mapper;

import com.cu.sci.lambdaserver.course.dto.DepartmentCoursesCollectingDto;
import com.cu.sci.lambdaserver.course.entites.DepartmentCourses;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DepartmentCoursesCollectingMapper implements IMapper<DepartmentCourses, DepartmentCoursesCollectingDto> {
    @Override
    public DepartmentCoursesCollectingDto mapTo(DepartmentCourses departmentCourses) {

        DepartmentCoursesCollectingDto departmentCoursesDto = new DepartmentCoursesCollectingDto();
        departmentCoursesDto.setCode(departmentCourses.getCourse().getCode());
        departmentCoursesDto.setName(departmentCourses.getCourse().getName());
        departmentCoursesDto.setInfo(departmentCourses.getCourse().getInfo());
        departmentCoursesDto.setCreditHours(departmentCourses.getCourse().getCreditHours());
        departmentCoursesDto.setSemester(departmentCourses.getSemester());
        departmentCoursesDto.setMandatory(departmentCourses.getMandatory());
        departmentCoursesDto.setDepartmentCode(departmentCourses.getDepartment().getDepartmentCourses().stream().map(departmentCourses1 -> departmentCourses1.getDepartment().getCode()).collect(Collectors.toSet()));
        return departmentCoursesDto;

    }

    @Override
    public DepartmentCourses mapFrom(DepartmentCoursesCollectingDto departmentCoursesCollectingDto) {
        return null;
    }

    @Override
    public DepartmentCourses update(DepartmentCoursesCollectingDto departmentCoursesCollectingDto, DepartmentCourses departmentCourses) {
        return null;
    }
}
