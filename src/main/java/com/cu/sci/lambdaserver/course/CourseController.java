package com.cu.sci.lambdaserver.course;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.course.dto.CreateCourseRequestDto;
import com.cu.sci.lambdaserver.course.dto.DepartmentCoursesCollectingDto;
import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.course.service.ICourseService;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = ("/api/v1/courses"))
@RequiredArgsConstructor
public class CourseController {
    private final ICourseService courseService;
    private final IMapper<Course, CourseDto> courseMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDto createCourse(@Valid @RequestBody CreateCourseRequestDto course) {
        return courseService.createCourse(course);
    }

    @GetMapping("/{courseCode}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDto getCourseByCode(@PathVariable String courseCode) {
        return courseService.getCourseByCode(courseCode);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CourseDto> getCourses(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "20") Integer pageSize) {
        return courseService.getCourses(pageNo, pageSize);
    }

    @DeleteMapping("/{courseCode}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse deleteCourseByCode(@PathVariable String courseCode) {
        return courseService.deleteCourseByCode(courseCode);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Set<CourseDto> search(@RequestParam() String q) {
        return courseService.search("%" + q + "%");
    }


    @PutMapping(path = "/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDto updateCourse(@Valid @PathVariable(name = "id") String courseCode,
                                  @Valid @RequestBody CourseDto course) {
        return courseMapper.mapTo(courseService.updateCourse(courseCode, courseMapper.mapFrom(course)));
    }

    @PostMapping("/{id}/prerequisites/{prerequisite}")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDto addPrerequisite(@PathVariable(name = "id") String courseCode, @PathVariable(name = "prerequisite") String prerequisiteCode) {
        return courseMapper.mapTo(courseService.addPrerequisite(courseCode, prerequisiteCode));
    }

    @GetMapping("/{id}/prerequisites")
    @ResponseStatus(HttpStatus.OK)
    public Set<CourseDto> getPrerequisite(@PathVariable(name = "id") String courseCode) {
        return courseService.getPrerequisite(courseCode).stream().map(courseMapper::mapTo).collect(Collectors.toSet());
    }

    @GetMapping("/{id}/prerequisites/all")
    @ResponseStatus(HttpStatus.OK)
    public Set<CourseDto> getAllPrerequisite(@PathVariable(name = "id") String courseCode) {
        return courseService.getAllPrerequisites(courseCode).stream().map(courseMapper::mapTo).collect(Collectors.toSet());
    }

    @DeleteMapping("/{id}/prerequisites/{prerequisite}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDto removePrerequisite(@PathVariable(name = "id") String courseCode, @PathVariable(name = "prerequisite") String prerequisiteCode) {
        return courseMapper.mapTo(courseService.removePrerequisite(courseCode, prerequisiteCode));
    }

    @PutMapping("/{id}/{department}")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentCoursesCollectingDto changeMandatoryAndSemester(@PathVariable(name = "id") String courseCode, @PathVariable(name = "department") String departmentCode,
                                                                     @RequestParam(name = "mandatory") Boolean mandatory, @RequestParam(name = "semester") String semester) {
        return courseService.changeMandatoryAndSemester(courseCode, departmentCode, mandatory, semester);
    }
}
