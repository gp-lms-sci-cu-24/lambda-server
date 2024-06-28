package com.cu.sci.lambdaserver.courseclass.mapper;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassTimingDto;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClassTiming;
import com.cu.sci.lambdaserver.professor.Professor;
import com.cu.sci.lambdaserver.professor.dto.ProfessorDto;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Slf4j
public class CourseClassMapper implements IMapper<CourseClass, CourseClassDto> {
    private final IMapper<Course, CourseDto> courseMapper;
    private final IMapper<Professor, ProfessorDto> professorMapper;
    private final IMapper<CourseClassTiming, CourseClassTimingDto> courseClassTimingMapper;

    @Override
    public CourseClassDto mapTo(CourseClass courseClass) {
        return mapTo(courseClass, Set.of(Include.ADMIN_PROFESSOR));
    }

    public CourseClassDto mapTo(CourseClass courseClass, Set<Include> includes) {
        CourseClassDto dto = CourseClassDto.builder()
                .id(courseClass.getId())
                .semester(courseClass.getSemester())
                .state(courseClass.getState())
                .maxCapacity(courseClass.getMaxCapacity())
                .numberOfStudentsRegistered(courseClass.getNumberOfStudentsRegistered())
                .year(courseClass.getYear())
                .timings(courseClass.getTimings().stream()
                        .map(courseClassTimingMapper::mapTo)
                        .collect(Collectors.toSet())
                )
                .professors(courseClass.getProfessors().stream()
                        .map(professorMapper::mapTo)
                        .collect(Collectors.toSet())
                )
                .adminProfessor(professorMapper.mapTo(courseClass.getAdminProfessor()))
                .course(courseMapper.mapTo(courseClass.getCourse()))
                .groupNumber(courseClass.getGroupNumber())
                .build();


        if (includes.contains(Include.COURSE)) {
            dto.setCourse(courseMapper.mapTo(courseClass.getCourse()));
        } else {
            dto.setCourse(CourseDto.builder()
                    .code(courseClass.getCourse().getCode())
                    .name(courseClass.getCourse().getName())
                    .image(courseClass.getCourse().getImage())
                    .build());
        }
        if (includes.contains(Include.ADMIN_PROFESSOR)) {
            dto.setAdminProfessor(professorMapper.mapTo(courseClass.getAdminProfessor()));
        } else {
            dto.setAdminProfessor(ProfessorDto.builder()
                    .username(courseClass.getAdminProfessor().getUsername())
                    .firstName(courseClass.getAdminProfessor().getFirstName())
                    .lastName(courseClass.getAdminProfessor().getLastName())
                    .profilePicture(courseClass.getAdminProfessor().getProfilePicture())
                    .build()
            );
        }
        if (includes.contains(Include.TIMINGS)) {
            dto.setTimings(courseClass.getTimings().stream()
                    .map(courseClassTimingMapper::mapTo)
                    .collect(Collectors.toSet()));
        }
        if (includes.contains(Include.PROFESSORS)) {
            dto.setProfessors(courseClass.getProfessors().parallelStream()
                    .map(professorMapper::mapTo)
                    .collect(Collectors.toSet()));
        }

        return dto;
    }

    @Override
    public CourseClass mapFrom(CourseClassDto courseClassDto) {
        throw new UnsupportedOperationException("not supported Yet");
    }

    @Override
    public CourseClass update(CourseClassDto courseClassDto, CourseClass courseClass) {
        throw new UnsupportedOperationException("Update not supported");
    }

    public enum Include {
        COURSE, TIMINGS, ADMIN_PROFESSOR, PROFESSORS,
    }
}