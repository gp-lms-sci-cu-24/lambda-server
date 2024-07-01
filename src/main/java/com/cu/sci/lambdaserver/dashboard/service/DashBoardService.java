package com.cu.sci.lambdaserver.dashboard.service;

import com.cu.sci.lambdaserver.course.repositries.CourseRepository;
import com.cu.sci.lambdaserver.courseclass.repository.CourseClassRepository;
import com.cu.sci.lambdaserver.dashboard.dto.DashBoardDto;
import com.cu.sci.lambdaserver.dashboard.dto.StudentCountByYearDto;
import com.cu.sci.lambdaserver.department.DepartmentRepository;
import com.cu.sci.lambdaserver.location.LocationRepository;
import com.cu.sci.lambdaserver.professor.ProfessorRepository;
import com.cu.sci.lambdaserver.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<StudentCountByYearDto> getStudentCountByYear() {
       List<String> years = studentRepository.getDistinctJoiningYear().stream().toList() ;

         return years.stream().map(year -> {
              long count = studentRepository.countByJoiningYear(year);
              return StudentCountByYearDto.builder()
                     .year(year)
                     .count(count)
                     .build();
         }).toList();
    }
}
