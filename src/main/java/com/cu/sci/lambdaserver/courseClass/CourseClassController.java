package com.cu.sci.lambdaserver.courseClass;

import com.cu.sci.lambdaserver.courseClass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseClass.mapper.iMapper;
import com.cu.sci.lambdaserver.courseClass.service.CourseClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/classes")
public class CourseClassController {
    private iMapper<CourseClass,CourseClassDto> courseClassMapper;
    private CourseClassService courseClassService;
    public CourseClassController(iMapper<CourseClass, CourseClassDto> courseClassMapper,
                                 CourseClassService courseClassService) {
        this.courseClassMapper = courseClassMapper;
        this.courseClassService = courseClassService;
    }
    @PostMapping
    public ResponseEntity<CourseClassDto> createCourseClass(@RequestBody CourseClassDto courseClassDto) {
        CourseClass courseClassEntity = courseClassMapper.mapFrom(courseClassDto);
        AtomicInteger groupNumberSeq = new AtomicInteger(1);
        courseClassService.getLatestClassByCourseId(courseClassDto.getCourseId() )
            .ifPresent((courseClass)->{
                if(LocalDateTime.now().getYear() == courseClass.getPublishDate().getYear()
                && courseClass.getCourseSemester() == courseClassEntity.getCourseSemester() ){
                    groupNumberSeq.set(courseClass.getGroupNumber() + 1 );
                }
//                if(ChronoUnit.DAYS.between(courseClass.getPublishDate(), LocalDate.now() ) < 60){
//                    groupNumberSeq.set(courseClass.getGroupNumberSeq() + 1 );
//                }
//                System.out.println(courseClass);
//                System.out.println(groupNumberSeq.get() );
            });

//        return new ResponseEntity<>(HttpStatus.OK);
        courseClassEntity.setGroupNumber(groupNumberSeq.get() );
        CourseClass savedCourseClass = courseClassService.createCourseClass(courseClassEntity);
        return new ResponseEntity<>(courseClassMapper.mapTo(savedCourseClass), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<CourseClassDto>> getAllCourseClasses() {
        List<CourseClass> courseClasses = courseClassService.getAllCourseClasses();
        return new ResponseEntity<>(courseClasses.stream().map(courseClassMapper::mapTo).collect(Collectors.toList())
                , HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CourseClassDto> getCourseClassById(@PathVariable Long id) {
        Optional<CourseClass> courseClassOptional = courseClassService.getCourseClassById(id);
        return courseClassOptional.map(courseClass -> new ResponseEntity<>(courseClassMapper.mapTo(courseClass), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<CourseClassDto> updateCourseClass(@PathVariable Long id, @RequestBody CourseClassDto courseClassDto) {
        if (!courseClassService.isCourseClassExists(id) ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CourseClass courseClassEntity = courseClassMapper.mapFrom(courseClassDto);
        CourseClass updatedCourseClass = courseClassService.updateCourseClass(id, courseClassEntity);

        return new ResponseEntity<>(courseClassMapper.mapTo(updatedCourseClass), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourseClass(@PathVariable Long id) {
        if (!courseClassService.isCourseClassExists(id) ) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
        courseClassService.deleteCourseClass(id);
        return new ResponseEntity<>("The record was deleted succesfully", HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CourseClass> updateCourseClass(@PathVariable Long id, @RequestBody CourseClass courseClass) {
        CourseClass updatedCourseClass = courseClassService.updateCourseClass(id, courseClass);
        return new ResponseEntity<>(updatedCourseClass, HttpStatus.OK);
    }

    @PostMapping("/init")
    public ResponseEntity<String> initializeCourseClasses() {
        courseClassService.init();
        return new ResponseEntity<String>("inited succesfully", HttpStatus.OK);
    }
}
