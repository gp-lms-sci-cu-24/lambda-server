package com.cu.sci.lambdaserver.courseclass.service;


import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.course.repositries.CourseRepository;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassInDto;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassResponse;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseclass.mapper.CourseClassMapper;
import com.cu.sci.lambdaserver.courseclass.mapper.CourseClassResponseMapper;
import com.cu.sci.lambdaserver.courseclass.repository.CourseClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class CourseClassService implements ICourseClassService {

    private final CourseClassRepository courseClassRepository;
    private final CourseClassMapper courseClassMapper;
    private final CourseRepository courseRepository;
    private final CourseClassResponseMapper courseClassResponseMapper;




    public CourseClass saveCourseClass(CourseClass courseClass) {
        return courseClassRepository.save(courseClass);
    }

    public Optional<CourseClass> getLatestClassByCourseId(Long id) {
        return courseClassRepository.getLatestClassByCourseId(id);
    }










    @Override
    public CourseClassResponse createCourseClass(CourseClassDto courseClassDto) {

        //check if the course exist
        Optional<Course> course = courseRepository.findById(courseClassDto.getCourseId());
        if (course.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course not found with this id");
        }

        //get course code
        String courseCode = course.get().getCode();

        //create the course class
        CourseClass courseClassEntity = courseClassMapper.mapFrom(courseClassDto);
        AtomicInteger groupNumberSeq = new AtomicInteger(1);

                getLatestClassByCourseId(courseClassDto.getCourseId())
                .ifPresent((courseClass) -> {
                    if (LocalDateTime.now().getYear() == courseClass.getPublishDate().getYear()
                            && courseClass.getCourseSemester() == courseClassEntity.getCourseSemester()) {
                        groupNumberSeq.set(courseClass.getGroupNumber() + 1);
                    }
                });

                courseClassEntity.setGroupNumber(groupNumberSeq.get());
                courseClassEntity.setCourse(course.get());
                CourseClass courseClass = saveCourseClass(courseClassEntity);
                CourseClassResponse courseClassResponse = courseClassResponseMapper.mapFrom(courseClass);
                courseClassResponse.setCourseCode(courseCode);
                courseClassResponse.setCourseName(course.get().getName());
                return courseClassResponse;

    }

    @Override
    public List<CourseClass> getAllCourseClasses() {
        return courseClassRepository.findAll();
    }

    @Override
    public CourseClass getCourseClassById(Long id) {
        return courseClassRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this id"));
    }

    @Override
    public boolean isCourseClassExists(Long id) {
        return courseClassRepository.existsById(id);
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
    public void deleteCourseClass(Long id) {
        courseClassRepository.deleteById(id);
    }




}
