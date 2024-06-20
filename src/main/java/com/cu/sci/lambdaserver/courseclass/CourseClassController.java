package com.cu.sci.lambdaserver.courseclass;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassResponseDto;
import com.cu.sci.lambdaserver.courseclass.dto.CreateCourseClassRequestDto;
import com.cu.sci.lambdaserver.courseclass.mapper.CourseClassMapper;
import com.cu.sci.lambdaserver.courseclass.service.CourseClassTimingService;
import com.cu.sci.lambdaserver.courseclass.service.ICourseClassService;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.CourseClassState;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/classes")
@RequiredArgsConstructor
@Slf4j
public class CourseClassController {
    private final ICourseClassService courseClassService;
    private final CourseClassTimingService courseClassTimingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseClassResponseDto createCourseClass(@Valid @RequestBody CreateCourseClassRequestDto courseClassDto) {
        return courseClassService.createCourseClass(courseClassDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CourseClassResponseDto> getAllCourseClasses(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "") Set<CourseClassMapper.Include> includes,
            @RequestParam(defaultValue = "REGISTRATION,IN_PROGRESS") Set<CourseClassState> states,
            @RequestParam(defaultValue = "FIRST,SECOND,SUMMER") Set<YearSemester> semesters,
            @RequestParam(defaultValue = "") Set<Integer> years,
            @RequestParam(defaultValue = "") String professor
    ) {

        return courseClassService.getAllCourseClasses(pageNo, pageSize, includes, states, semesters, years, professor);
    }

    @GetMapping("{courseCode}")
    @ResponseStatus(HttpStatus.OK)
    public Page<CourseClassResponseDto> getAllCourseClassesByCourseCode(
            @PathVariable("courseCode") String courseCode,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "") Set<CourseClassMapper.Include> includes,
            @RequestParam(defaultValue = "REGISTRATION,IN_PROGRESS") Set<CourseClassState> states,
            @RequestParam(defaultValue = "FIRST,SECOND,SUMMER") Set<YearSemester> semesters,
            @RequestParam(defaultValue = "") Set<Integer> years,
            @RequestParam(defaultValue = "") String professor
    ) {
        return courseClassService.getCourseClassByCourse(pageNo, pageSize, courseCode, includes, states, semesters, years, professor);
    }

    @GetMapping("{courseCode}/{year}")
    @ResponseStatus(HttpStatus.OK)
    public Page<CourseClassResponseDto> getAllCourseClassesByCourseCodeAndYear(
            @PathVariable("courseCode") String courseCode,
            @PathVariable("year") Integer year,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "") Set<CourseClassMapper.Include> includes,
            @RequestParam(defaultValue = "REGISTRATION,IN_PROGRESS") Set<CourseClassState> states,
            @RequestParam(defaultValue = "FIRST,SECOND,SUMMER") Set<YearSemester> semesters,
            @RequestParam(defaultValue = "") String professor
    ) {
        return courseClassService.getCourseClassByCourse(pageNo, pageSize, courseCode, includes, states, semesters, Set.of(year), professor);
    }

    @GetMapping("{courseCode}/{year}/{semester}")
    @ResponseStatus(HttpStatus.OK)
    public Page<CourseClassResponseDto> getAllCourseClassesByCourseCodeAndYearAndSemester(
            @PathVariable("courseCode") String courseCode,
            @PathVariable("year") Integer year,
            @PathVariable("semester") YearSemester semester,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "") Set<CourseClassMapper.Include> includes,
            @RequestParam(defaultValue = "REGISTRATION,IN_PROGRESS") Set<CourseClassState> states,
            @RequestParam(defaultValue = "") String professor
    ) {
        return courseClassService.getCourseClassByCourse(pageNo, pageSize, courseCode, includes, states, Set.of(semester), Set.of(year), professor);
    }

    @GetMapping("{courseCode}/{year}/{semester}/{group}")
    @ResponseStatus(HttpStatus.OK)
    public CourseClassResponseDto getAllCourseClassesByCourseCodeAndYearAndSemester(
            @PathVariable("courseCode") String courseCode,
            @PathVariable("year") Integer year,
            @PathVariable("semester") YearSemester semester,
            @PathVariable("group") Integer group
    ) {
        return courseClassService.getCourseClassByCourseAndYearAndSemesterAndGroup(courseCode, year, semester, group);
    }

    @DeleteMapping("{courseCode}/{year}/{semester}/{group}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse deleteAllCourseClassesByCourseCodeAndYearAndSemester(
            @PathVariable("courseCode") String courseCode,
            @PathVariable("year") Integer year,
            @PathVariable("semester") YearSemester semester,
            @PathVariable("group") Integer group
    ) {
        return courseClassService.deleteCourseClassByCourseAndYearAndSemesterAndGroup(courseCode, year, semester, group);
    }


//    @GetMapping("/{course-code}/{group-number}")
//    @ResponseStatus(HttpStatus.OK)
//    public CourseClassResponseDto getCourseClassById(@PathVariable("course-code") String courseCode, @PathVariable("group-number") Integer groupNumber) {
//        return courseClassService.getCourseClass(courseCode, groupNumber);
//    }

//    @GetMapping("/{course-code}/{semester}/{year}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<CourseClassResponseDto> getCourseClassByCourseCodeAndSemesterAndYear(@PathVariable("course-code") String courseCode, @PathVariable("semester") YearSemester semester, @PathVariable("year") Integer year) {
//        return courseClassService.getCourseClassesByCourseCodeAndSemester(courseCode, semester, year).stream().toList();
//    }

//    @PatchMapping("/{course-code}/{group-number}")
//    @ResponseStatus(HttpStatus.OK)
//    public CourseClassResponseDto updateCourseClass(@PathVariable("course-code") String courseCode, @PathVariable("group-number") Integer groupNumber,@Validated(CreateCourseClassDto.UpdateValidation.class) @RequestBody CreateCourseClassDto courseClassDto) {
//    return courseClassService.updateCourseClass(courseCode, groupNumber, courseClassDto);
//    }

//    @DeleteMapping("/{course-code}/{group-number}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public MessageResponse deleteCourseClass(@PathVariable("course-code") String courseCode, @PathVariable("group-number") Integer groupNumber) {
//        return  courseClassService.deleteCourseClass(courseCode, groupNumber);
//    }

//    @GetMapping("/timing")
//    @ResponseStatus(HttpStatus.OK)
//    public List<CourseClassTimingOutDto> getCourseClassTiming() {
//        return courseClassTimingService.getAllCourseClassTiming();
//    }

//    @PostMapping("/timing")
//    @ResponseStatus(HttpStatus.CREATED)
//    public CourseClassTimingOutDto createCourseClassTiming(@Valid @RequestBody CourseClassTimingInDto courseClassTimingInDto) {
//        return courseClassTimingService.addCourseClassTiming(courseClassTimingInDto);
//    }

//    @GetMapping("/timing/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public CourseClassTimingOutDto getCourseClassTimingById(@PathVariable Long id) {
//        return courseClassTimingService.getCourseClassTimingById(id);
//    }

//    @PutMapping({"/timing/{id}"})
//    @ResponseStatus(HttpStatus.OK)
//    public CourseClassTimingOutDto updateCourseClassTiming(@PathVariable Long id, @RequestBody CourseClassTimingInDto courseClassTimingInDto) {
//        return courseClassTimingService.updateCourseClassTiming(id, courseClassTimingInDto);
//    }

//    @DeleteMapping({"/timing/{id}"})
//    @ResponseStatus(HttpStatus.OK)
//    public CourseClassTimingOutDto deleteCourseClassTiming(@PathVariable Long id) {
//        return courseClassTimingService.deleteCourseClassTiming(id);
//    }
}
