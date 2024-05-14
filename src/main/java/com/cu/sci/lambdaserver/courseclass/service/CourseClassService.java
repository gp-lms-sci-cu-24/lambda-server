package com.cu.sci.lambdaserver.courseclass.service;


import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.course.repositries.CourseRepository;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.dto.CreateCourseClassDto;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassResponse;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseclass.mapper.CourseClassMapper;
import com.cu.sci.lambdaserver.courseclass.mapper.CourseClassResponseMapper;
import com.cu.sci.lambdaserver.courseclass.mapper.CreateCourseClassMapper;
import com.cu.sci.lambdaserver.courseclass.repository.CourseClassRepository;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class CourseClassService implements ICourseClassService {

    private final CourseClassRepository courseClassRepository;
    private final CourseClassMapper courseClassMapper;
    private final CreateCourseClassMapper createCourseClassMapper ;
    private final CourseRepository courseRepository;
    private final CourseClassResponseMapper courseClassResponseMapper;








    @Override
    public CourseClassDto  createCourseClass(CreateCourseClassDto courseClassDto) {

        //check if the course exist
        Optional<Course> course = courseRepository.findByCode(courseClassDto.getCourseCode());
        if (course.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course not found with this code " + courseClassDto.getCourseCode());
        }

        //get current year
        String year = Year.now().toString();

        //create the course class
        CourseClass courseClassEntity = createCourseClassMapper.mapFrom(courseClassDto);

        AtomicInteger groupNumberSeq = new AtomicInteger(1);

        courseClassRepository.findTopByCourseIdOrderByGroupNumberDesc(course.get().getId())
                .ifPresent((courseClass) -> {
                    if (LocalDateTime.now().getYear() == courseClass.getPublishDate().getYear()
                            && courseClass.getCourseSemester() == courseClassEntity.getCourseSemester()) {
                        groupNumberSeq.set(courseClass.getGroupNumber() + 1);
                    }
                });

        courseClassEntity.setGroupNumber(groupNumberSeq.get());
        courseClassEntity.setCourse(course.get());
        courseClassEntity.setYear(year);
        CourseClass courseClass = courseClassRepository.save(courseClassEntity);

        CourseClassDto courseClassDtoResponse =  courseClassMapper.mapTo(courseClass);
        courseClassDtoResponse.setCourseCode(course.get().getCode());
        return  courseClassDtoResponse;

    }




    @Override
    public CourseClassDto getCourseClass(String courseCode , Integer groupNumber) {
        //check if the course exist
        Optional<Course> course = courseRepository.findByCodeIgnoreCase(courseCode);
        if (course.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course not found with this code " + courseCode);
        }

        Optional<CourseClass> courseClass = courseClassRepository.findByCourseIdAndGroupNumber(course.get().getId(), groupNumber);
        if (courseClass.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this group number " + groupNumber);
        }

        return courseClassMapper.mapTo(courseClass.get());
    }








    @Override
    public CourseClass updateCourseClass(CourseClassDto courseClassDto) {
        // make sure that the courseClass exist
        Long id = courseClassDto.getCourseClassId();
        CourseClass courseClass = courseClassRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this id"));
        courseClassMapper.update(courseClassDto, courseClass);
        return courseClassRepository.save(courseClass);
    }



    @Override
    public MessageResponse deleteCourseClass(String courseCode , Integer groupNumber) {
        //check if the course exist
        Optional<Course> course = courseRepository.findByCodeIgnoreCase(courseCode);
        if (course.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course not found with this code " + courseCode);
        }

        Optional<CourseClass> courseClass = courseClassRepository.findByCourseIdAndGroupNumber(course.get().getId(), groupNumber);
        if (courseClass.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this group number " + groupNumber);
        }

        courseClassRepository.delete(courseClass.get());

        return new MessageResponse("course class deleted successfully");
    }




}
