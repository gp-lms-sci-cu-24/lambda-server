package com.cu.sci.lambdaserver.courseregister;

import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterInDto;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterOutDto;
import com.cu.sci.lambdaserver.courseregister.service.CourseRegisterService;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.CourseRegisterState;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/v1/course-register")
@RequiredArgsConstructor
public class CourseRegisterController {
    private final CourseRegisterService courseRegisterService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseRegisterOutDto createCourseRegister(@Validated(CourseRegisterInDto.CreateValidation.class) @RequestBody CourseRegisterInDto courseRegisterInDto) {
        return courseRegisterService.createCourseRegister(courseRegisterInDto);
    }


    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public Collection<CourseRegisterOutDto> studentGetAllCourseRegisters() {
        return courseRegisterService
                .studentGetAllCourseRegisters();
    }
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseRegisterOutDto getCourseRegister(@PathVariable Long id) {
        return courseRegisterService.getCourseRegister(id);
    }

    @GetMapping(path = "/{code}/registers")
    @ResponseStatus(HttpStatus.OK)
    public Collection<CourseRegisterOutDto> getAllCourseRegisters(@PathVariable String code, @RequestParam CourseRegisterState state) {
        System.out.println(state);
        return courseRegisterService.getCourseRegistersByState(code, state) ;
    }


    @GetMapping(path = "/student/{code}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<CourseRegisterOutDto> getAllStudentCourseRegisters(@PathVariable String code) {
        return courseRegisterService
                .getStudentRegisteredCourses(code);
    }

    @GetMapping(path = "/class/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<StudentDto> getAllCourseClassStudents(@PathVariable Long id) {
        return courseRegisterService
                .getAllCourseClassStudents(id);
    }

    @GetMapping(path = "/result/{year}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<CourseRegisterOutDto> getAllCourseRegistersBySemesterAndYear(@RequestParam YearSemester semester, @PathVariable String year) {
        return courseRegisterService.getMyReslut(semester , year);
    }

    @PatchMapping(path = "/{course-register-id}/{course-class-id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseRegisterOutDto updateCourseRegister(@PathVariable("course-register-id")Long courseRegisterId , @PathVariable("course-class-id") Long courseClassId) {
        return courseRegisterService.updateCourseRegister(courseClassId , courseRegisterId);
    }

    @PatchMapping(path = "/confirm/{student-code}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse confrimAdminCourseRegister(@PathVariable("student-code") String studentCode) {
        return courseRegisterService.adminConfirmCourseRegister(studentCode);
    }

    @PatchMapping(path = "/confirm/me")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse confrimStudentCourseRegister() {
        return courseRegisterService.studentConfirmCourseRegister();
    }

    @PatchMapping(path = "/grade/{student-code}/{course-class-id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse addGrade(@PathVariable("student-code") String studentCode ,@PathVariable("course-class-id") Long courseClassId , @RequestBody  CourseRegisterInDto courseRegisterInDto) {
        return courseRegisterService.addGrade(studentCode , courseClassId , courseRegisterInDto.getGrade());
    }

    @DeleteMapping(path = "/me/{course-class-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public MessageResponse deleteMyCourseRegister(@PathVariable("course-class-id") Long courseClassId) {
        return courseRegisterService.deleteCourseRegister(courseClassId);
    }

    @DeleteMapping(path = "/{student-code}/{course-class-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public MessageResponse deleteCourseRegisterByStudentCode(@PathVariable("student-code") String studentCode , @PathVariable("course-class-id") Long courseClassId) {
        return courseRegisterService.deleteCourseRegisterByStudentCode(studentCode , courseClassId);
    }

}