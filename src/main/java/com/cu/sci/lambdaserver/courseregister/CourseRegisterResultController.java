package com.cu.sci.lambdaserver.courseregister;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.courseregister.service.IAuthenticatedCourseRegisterResultService;
import com.cu.sci.lambdaserver.courseregister.service.ICourseRegisterService;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class CourseRegisterResultController {

    //    private final CourseRegisterSessionRepository courseRegisterSessionRepository;
//    private final CourseClassRepository courseClassRepository;
//    private final StudentRepository studentRepository;
    private final ICourseRegisterService courseRegisterService;
    private final IAuthenticatedCourseRegisterResultService authenticatedCourseRegisterResultService;

    @GetMapping("register/me/courses")
    public Set<CourseDto> getAvailableCoursesForMe() {
        return authenticatedCourseRegisterResultService.getMyAvailableCourses();
    }

    @PostMapping("register/me/seat/{course}/{year}/{semester}/{groupNumber}")
    public MessageResponse takeASeatForMe(
            @PathVariable String course,
            @PathVariable Integer year,
            @PathVariable YearSemester semester,
            @PathVariable Integer groupNumber
    ) {
        return authenticatedCourseRegisterResultService.takeASeatToMeAtCourseClass(course, year, semester, groupNumber);
    }

    @PostMapping("register/me/class/{course}/{year}/{semester}/{groupNumber}")
    public MessageResponse registerForMe(
            @PathVariable String course,
            @PathVariable Integer year,
            @PathVariable YearSemester semester,
            @PathVariable Integer groupNumber
    ) {
        return authenticatedCourseRegisterResultService.registerCourseClassToMe(course, year, semester, groupNumber);
    }

    @GetMapping("register/courses/{student}")
    public Set<CourseDto> getAvailableCourses(@PathVariable String student) {
        return courseRegisterService.getStudentAvailableCourses(student);
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