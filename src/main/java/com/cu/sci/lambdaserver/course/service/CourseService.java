package com.cu.sci.lambdaserver.course.service;

import com.cu.sci.lambdaserver.course.CourseRepository;
import com.cu.sci.lambdaserver.course.entites.Course;
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

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }


    public Course getCourse(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent())
            return optionalCourse.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "the course with id : " + courseId + " doesn't exist");
    }

    public Course createCourse(Course course) {
        Optional<Course> courseWithSameCode = courseRepository.findByCode(course.getCode());
        if (courseWithSameCode.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "this code already exist " + course.getCode());
        return courseRepository.save(course);
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
