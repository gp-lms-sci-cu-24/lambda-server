package com.cu.sci.lambdaserver.course.mapper;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.course.dto.DepartmentCoursesDto;
import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.course.entites.DepartmentCourses;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseMapper implements IMapper<Course, CourseDto> {
    private final ModelMapper modelMapper;

    public CourseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CourseDto mapTo(Course course) {
        if (course == null)
            return new CourseDto();

        List<String> coursePrerequisitesCode = new ArrayList<>();
        List<DepartmentCoursesDto> courseDepartmentDto = new ArrayList<>();
        
        if (course.getCoursePrerequisites() != null) {
            for (Course coursePrerequisite : course.getCoursePrerequisites()) {
                coursePrerequisitesCode.add(coursePrerequisite.getCode());
            }
        }
        if (course.getDepartments() != null) {
            for (DepartmentCourses departmentCourses : course.getDepartments()) {
                courseDepartmentDto.add(modelMapper.map(departmentCourses, DepartmentCoursesDto.class));
            }
        }
        return CourseDto.builder()
                .code(course.getCode())
                .name(course.getName())
                .info(course.getInfo())
                .creditHours(course.getCreditHours())
                .departments(courseDepartmentDto)
                .image(course.getImage())
                .coursePrerequisites(coursePrerequisitesCode)
                .build();
    }

    @Override
    public Course mapFrom(CourseDto courseDto) {
        return modelMapper.map(courseDto, Course.class);
    }

    @Override
    public Course update(CourseDto courseDto, Course course) {
        return null;
    }
}

