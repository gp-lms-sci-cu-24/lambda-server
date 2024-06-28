package com.cu.sci.lambdaserver.courseregister;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseregister.dto.CourseResultDto;
import com.cu.sci.lambdaserver.courseregister.dto.CumulativeResultDto;
import com.cu.sci.lambdaserver.courseregister.service.IAuthenticatedCourseRegisterService;
import com.cu.sci.lambdaserver.courseregister.service.IAuthenticatedCourseResultsService;
import com.cu.sci.lambdaserver.courseregister.service.ICourseRegisterService;
import com.cu.sci.lambdaserver.courseregister.service.ICourseResultsService;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class CourseRegisterResultController {

    private final ICourseRegisterService courseRegisterService;
    private final ICourseResultsService courseResultsService;
    private final IAuthenticatedCourseRegisterService authenticatedCourseRegisterService;
    private final IAuthenticatedCourseResultsService authenticatedCourseResultsService;


    @GetMapping("register/me/class")
    public Set<CourseClassDto> getMyRegisteredCourseClass() {
        return authenticatedCourseRegisterService.getRegisteredCourseClassesToMe();
    }

    @GetMapping("register/me/courses")
    public Set<CourseDto> getAvailableCoursesForMe() {
        return authenticatedCourseRegisterService.getMyAvailableCourses();
    }

    @PostMapping("register/me/seat/{course}/{year}/{semester}/{groupNumber}")
    public MessageResponse takeASeatForMe(
            @PathVariable String course,
            @PathVariable Integer year,
            @PathVariable YearSemester semester,
            @PathVariable Integer groupNumber
    ) {
        return authenticatedCourseRegisterService.takeASeatToMeAtCourseClass(course, year, semester, groupNumber);
    }

    @PostMapping("register/me/class/{course}/{year}/{semester}/{groupNumber}")
    public MessageResponse registerForMe(
            @PathVariable String course,
            @PathVariable Integer year,
            @PathVariable YearSemester semester,
            @PathVariable Integer groupNumber
    ) {
        return authenticatedCourseRegisterService.registerCourseClassToMe(course, year, semester, groupNumber);
    }

    @DeleteMapping("register/me/class/{course}/{year}/{semester}/{groupNumber}")
    public MessageResponse removeRegisterForMe(
            @PathVariable String course,
            @PathVariable Integer year,
            @PathVariable YearSemester semester,
            @PathVariable Integer groupNumber
    ) {
        return authenticatedCourseRegisterService.removeCourseClassForMe(course, year, semester, groupNumber);
    }

    @GetMapping("register/courses/{student}")
    public Set<CourseDto> getAvailableCourses(@PathVariable String student) {
        return courseRegisterService.getStudentAvailableCourses(student);
    }

    @GetMapping("register/class/{student}")
    public Set<CourseClassDto> getRegisteredCourseClass(@PathVariable String student) {
        return courseRegisterService.getRegisteredCourseClasses(student);
    }

    @PostMapping("register/seat/{course}/{year}/{semester}/{groupNumber}/{student}")
    public MessageResponse takeASeatForStudent(
            @PathVariable String course,
            @PathVariable Integer year,
            @PathVariable YearSemester semester,
            @PathVariable Integer groupNumber,
            @PathVariable String student
    ) {
        return courseRegisterService.takeASeatAtCourseClass(student, course, year, semester, groupNumber);
    }

    @PostMapping("register/class/{course}/{year}/{semester}/{groupNumber}/{student}")
    public MessageResponse registerForStudent(
            @PathVariable String course,
            @PathVariable Integer year,
            @PathVariable YearSemester semester,
            @PathVariable Integer groupNumber,
            @PathVariable String student
    ) {
        return courseRegisterService.registerCourseClass(student, course, year, semester, groupNumber);
    }

    @DeleteMapping("register/class/{course}/{year}/{semester}/{groupNumber}/{student}")
    public MessageResponse removeRegisterForStudent(
            @PathVariable String course,
            @PathVariable Integer year,
            @PathVariable YearSemester semester,
            @PathVariable Integer groupNumber,
            @PathVariable String student
    ) {
        return courseRegisterService.removeCourseClass(student, course, year, semester, groupNumber);
    }

    /* -------------------
         Result
     --------------------- */
    @GetMapping("result/me")
    public Set<CourseResultDto> getMyResult() {
        return authenticatedCourseResultsService.getMyResult();
    }

    @GetMapping("result/me/{year}")
    public Set<CourseResultDto> getMyResultByYear(
            @PathVariable Integer year,
            @RequestParam(defaultValue = "FIRST,SECOND,SUMMER") Set<YearSemester> semesters
    ) {
        return authenticatedCourseResultsService.getMyResult(year, semesters);
    }

    @GetMapping("result/me/{year}/{semester}")
    public Set<CourseResultDto> getMyResultByYearAndSemester(
            @PathVariable Integer year,
            @PathVariable YearSemester semester
    ) {
        return authenticatedCourseResultsService.getMyResult(year, Set.of(semester));
    }


    @GetMapping("result/student/{student}")
    public Set<CourseResultDto> getStudentResult(
            @PathVariable String student
    ) {
        return courseResultsService.getStudentResult(student);
    }

    @GetMapping("result/student/{student}/{year}")
    public Set<CourseResultDto> getStudentResultByYear(
            @PathVariable String student,
            @PathVariable Integer year,
            @RequestParam(defaultValue = "FIRST,SECOND,SUMMER") Set<YearSemester> semesters
    ) {
        return courseResultsService.getStudentResult(student, year, semesters);
    }

    @GetMapping("result/student/{student}/{year}/{semester}")
    public Set<CourseResultDto> getStudentResultByYearAndSemester(
            @PathVariable String student,
            @PathVariable Integer year,
            @PathVariable YearSemester semester
    ) {
        return courseResultsService.getStudentResult(student, year, Set.of(semester));
    }

    @PostMapping("result/finish/{student}/{year}/{semester}/{group}/{grade}/{course}")
    public MessageResponse finishCourseClassForStudent(
            @PathVariable String student,
            @PathVariable Integer year,
            @PathVariable YearSemester semester,
            @PathVariable Integer group,
            @PathVariable Integer grade,
            @PathVariable String course
    ) {
        return courseResultsService.finishCourseClassForStudent(student, year, semester, group, grade, course);
    }

    @GetMapping("result/map/{student}")
    public List<List<CumulativeResultDto>> getStudentResultMap(
            @PathVariable String student
    ) {
        return courseResultsService.getStudentMapDepartment(student);
    }

//    @PostMapping
//    public Object takeASeat(){
//        CourseClass courseClass = courseClassRepository.findById(42L).get();
//        List<Student> students= studentRepository.findAll();
//
//        return courseRegisterSessionRepository.save(CourseRegisterSession.builder()
//                        .courseClass(courseClass)
//                        .student(students.get(0))
//                .build());
//    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public CourseRegisterOutDto createCourseRegister(@Validated(CourseRegisterInDto.CreateValidation.class) @RequestBody CourseRegisterInDto courseRegisterInDto) {
//        return courseRegisterService.createCourseRegister(courseRegisterInDto);
//    }
//
//
//    @GetMapping("/me")
//    @ResponseStatus(HttpStatus.OK)
//    public Collection<CourseRegisterOutDto> studentGetAllCourseRegisters() {
//        return courseRegisterService
//                .studentGetAllCourseRegisters();
//    }
//    @GetMapping(path = "/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public CourseRegisterOutDto getCourseRegister(@PathVariable Long id) {
//        return courseRegisterService.getCourseRegister(id);
//    }
//
//    @GetMapping(path = "/{code}/registers")
//    @ResponseStatus(HttpStatus.OK)
//    public Collection<CourseRegisterOutDto> getAllCourseRegisters(@PathVariable String code, @RequestParam CourseRegisterState state) {
//        System.out.println(state);
//        return courseRegisterService.getCourseRegistersByState(code, state) ;
//    }
//
//
//    @GetMapping(path = "/student/{code}")
//    @ResponseStatus(HttpStatus.OK)
//    public Collection<CourseRegisterOutDto> getAllStudentCourseRegisters(@PathVariable String code) {
//        return courseRegisterService
//                .getStudentRegisteredCourses(code);
//    }
//
//    @GetMapping(path = "/class/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Collection<StudentDto> getAllCourseClassStudents(@PathVariable Long id) {
//        return courseRegisterService
//                .getAllCourseClassStudents(id);
//    }
//
//    @GetMapping(path = "/result/{year}")
//    @ResponseStatus(HttpStatus.OK)
//    public Collection<CourseRegisterOutDto> getAllCourseRegistersBySemesterAndYear(@RequestParam YearSemester semester, @PathVariable Integer year) {
//        return courseRegisterService.getMyReslut(semester , year);
//    }
//
//    @PatchMapping(path = "/{course-register-id}/{course-class-id}")
//    @ResponseStatus(HttpStatus.OK)
//    public CourseRegisterOutDto updateCourseRegister(@PathVariable("course-register-id")Long courseRegisterId , @PathVariable("course-class-id") Long courseClassId) {
//        return courseRegisterService.updateCourseRegister(courseClassId , courseRegisterId);
//    }
//
//    @PatchMapping(path = "/confirm/{student-code}")
//    @ResponseStatus(HttpStatus.OK)
//    public MessageResponse confrimAdminCourseRegister(@PathVariable("student-code") String studentCode) {
//        return courseRegisterService.adminConfirmCourseRegister(studentCode);
//    }
//
//    @PatchMapping(path = "/confirm/me")
//    @ResponseStatus(HttpStatus.OK)
//    public MessageResponse confrimStudentCourseRegister() {
//        return courseRegisterService.studentConfirmCourseRegister();
//    }
//
//    @PatchMapping(path = "/grade/{student-code}/{course-class-id}")
//    @ResponseStatus(HttpStatus.OK)
//    public MessageResponse addGrade(@PathVariable("student-code") String studentCode ,@PathVariable("course-class-id") Long courseClassId , @RequestBody  CourseRegisterInDto courseRegisterInDto) {
//        return courseRegisterService.addGrade(studentCode , courseClassId , courseRegisterInDto.getGrade());
//    }
//
//    @DeleteMapping(path = "/me/{course-class-id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public MessageResponse deleteMyCourseRegister(@PathVariable("course-class-id") Long courseClassId) {
//        return courseRegisterService.deleteCourseRegister(courseClassId);
//    }
//
//    @DeleteMapping(path = "/{student-code}/{course-class-id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public MessageResponse deleteCourseRegisterByStudentCode(@PathVariable("student-code") String studentCode , @PathVariable("course-class-id") Long courseClassId) {
//        return courseRegisterService.deleteCourseRegisterByStudentCode(studentCode , courseClassId);
//    }

}