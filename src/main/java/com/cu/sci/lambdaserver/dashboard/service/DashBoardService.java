package com.cu.sci.lambdaserver.dashboard.service;

import com.cu.sci.lambdaserver.course.repositries.CourseRepository;
import com.cu.sci.lambdaserver.courseclass.repository.CourseClassRepository;
import com.cu.sci.lambdaserver.dashboard.DashBoardDto;
import com.cu.sci.lambdaserver.department.DepartmentRepository;
import com.cu.sci.lambdaserver.location.LocationRepository;
import com.cu.sci.lambdaserver.professor.ProfessorRepository;
import com.cu.sci.lambdaserver.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashBoardService implements IDashBoardService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final LocationRepository locationRepository ;
    private final DepartmentRepository departmentRepository ;
    private final CourseClassRepository courseClassRepository;





    @Override
    public DashBoardDto getDashBoard() {
        DashBoardDto dashBoardDto = DashBoardDto.builder()
                .totalCourses(courseRepository.count())
                .totalStudents(studentRepository.count())
                .totalProfessors(professorRepository.count())
                .totalLocations(locationRepository.count())
                .totalDepartments(departmentRepository.count())
                .totalCourseClasses(courseClassRepository.count())
                .build();

        return dashBoardDto;
    }
}
