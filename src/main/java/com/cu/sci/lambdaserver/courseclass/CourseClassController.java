package com.cu.sci.lambdaserver.courseclass;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.mapper.IMapper;
import com.cu.sci.lambdaserver.courseclass.mapper.IMapperV2;
import com.cu.sci.lambdaserver.courseclass.service.CourseClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/api/class")
@RequiredArgsConstructor
public class CourseClassController {
    private final IMapper<CourseClass,CourseClassDto> courseClassMapper;
    private final IMapperV2<CourseClass,CourseClassDto> courseClassMapperV2;
    private final CourseClassService courseClassService;
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
            });
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
    @PatchMapping
    public ResponseEntity<CourseClassDto> updateCourseClass(@RequestBody CourseClassDto courseClassDto) {
        Long id = courseClassDto.getCourseClassId();
        CourseClass presentCourseClass = courseClassService.getCourseClassById(id).orElse(null);
        if(presentCourseClass == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        System.out.println(presentCourseClass);
        CourseClass updatedCourseClass = courseClassService.saveCourseClass(courseClassMapperV2.update(courseClassDto,presentCourseClass) );
        System.out.println(updatedCourseClass);
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
