package com.cu.sci.lambdaserver.course.service;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.course.dto.CreateCourseDto;
import com.cu.sci.lambdaserver.course.entites.DepartmentCourses;
import com.cu.sci.lambdaserver.course.entites.DepartmentCoursesKey;
import com.cu.sci.lambdaserver.course.repositries.CourseRepository;
import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.course.repositries.DepartmentCoursesRepository;
import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.department.DepartmentRepository;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService implements ICourseService {
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final DepartmentCoursesRepository departmentCoursesRepository;
    private final iMapper<Course, CourseDto> courseMapper;

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }


    public Course getCourse(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent())
            return optionalCourse.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "the course with id : " + courseId + " doesn't exist");
    }

    public CourseDto createCourse(CreateCourseDto createCourseDto) {
        //check if the course code is already exist
        Optional<Course> courseWithSameCode = courseRepository.findByCode(createCourseDto.getCode());
        if (courseWithSameCode.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, " this code already exist " + createCourseDto.getCode());
        }

        //check if the department code if exist in department table
        createCourseDto.getDepartmentCode().forEach(departmentCode -> {
            Optional<Department> department = departmentRepository.findDepartmentByCodeIgnoreCase(departmentCode) ;
            if (department.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "this department code doesn't exist " + departmentCode);
            }
        });


        //get the department object from department table
        List<Department> departmentList = createCourseDto.getDepartmentCode().stream()
                .map(departmentCode -> departmentRepository
                        .findDepartmentByCodeIgnoreCase(departmentCode)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department code not found: " + departmentCode)))
                .toList();



        //create course and save it in course table
        Course course = Course.builder()
                .name(createCourseDto.getName())
                .code(createCourseDto.getCode())
                .info(createCourseDto.getInfo())
                .creditHours(createCourseDto.getCreditHours())
                .build();
        Course courseSaved = courseRepository.save(course);



        //create departmentCourses and save it in department_courses table for each department
        departmentList.forEach(department -> {
            DepartmentCourses departmentCourses = DepartmentCourses.builder()
                    .departmentCoursesKey(new DepartmentCoursesKey(department.getId(), courseSaved.getId()))
                    .course(courseSaved)
                    .department(department)
                    .semester(createCourseDto.getSemester())
                    .mandatory(createCourseDto.getMandatory())
                    .build();
            departmentCoursesRepository.save(departmentCourses);
        });

        return courseMapper.mapTo(courseSaved);
    }

    public Course deleteCourse(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            courseRepository.deleteById(courseId);
            return optionalCourse.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "the course with id : " + courseId + " doesn't exist ");
    }

    @Override
    public Course updateCourse(Long courseId, Course course) {
        return courseRepository.findById(courseId).map(currentCourse -> {
            Optional.ofNullable(course.getName()).ifPresent(currentCourse::setName);
            Optional.ofNullable(course.getCreditHours()).ifPresent(currentCourse::setCreditHours);
            Optional.ofNullable(course.getDepartmentCoursesSet()).ifPresent(currentCourse::setDepartmentCoursesSet);
            Optional.ofNullable(course.getInfo()).ifPresent(currentCourse::setInfo);
            Optional<Course> courseWithSameCode = courseRepository.findByCode(course.getCode());
            if (courseWithSameCode.isPresent() && !Objects.equals(courseWithSameCode.get().getId(), courseId))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "this code already exist " + course.getCode());
            else
                Optional.ofNullable(course.getCode()).ifPresent(currentCourse::setCode);
            return courseRepository.save(currentCourse);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the course with id : " + courseId + " doesn't exist "));
    }
}
