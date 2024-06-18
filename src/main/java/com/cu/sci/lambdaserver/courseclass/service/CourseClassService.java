package com.cu.sci.lambdaserver.courseclass.service;


import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.course.repositries.CourseRepository;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.dto.CreateCourseClassDto;
import com.cu.sci.lambdaserver.courseclass.dto.CreateCourseClassRequestDto;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClassTiming;
import com.cu.sci.lambdaserver.courseclass.mapper.CourseClassMapper;
import com.cu.sci.lambdaserver.courseclass.mapper.CreateCourseClassMapper;
import com.cu.sci.lambdaserver.courseclass.repository.CourseClassRepository;
import com.cu.sci.lambdaserver.courseclass.repository.CourseClassTimingRepository;
import com.cu.sci.lambdaserver.location.Location;
import com.cu.sci.lambdaserver.location.LocationRepository;
import com.cu.sci.lambdaserver.professor.Professor;
import com.cu.sci.lambdaserver.professor.ProfessorRepository;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.ClassType;
import com.cu.sci.lambdaserver.utils.enums.CourseClassState;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Time;
import java.time.Year;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseClassService implements ICourseClassService {

    private final CourseClassMapper courseClassMapper;
    private final CreateCourseClassMapper createCourseClassMapper ;
    // repostiories
    private final CourseClassRepository courseClassRepository;
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;
    private final LocationRepository locationRepository;
    private final CourseClassTimingRepository courseClassTimingRepository;


    @Override
    public CourseClassDto createCourseClass(CreateCourseClassRequestDto dto) {

        // Values from the request
        int year = Optional.ofNullable(dto.year()).orElse(Year.now().getValue());
        CourseClassState state = Optional.ofNullable(dto.state()).orElse(CourseClassState.REGISTRATION);
        Optional<Course> course = courseRepository.findByCodeIgnoreCase(dto.courseCode());
        Optional<Professor> professor = professorRepository.findByUsernameIgnoreCase(dto.adminProfessor());
        Set<CourseClassTiming> timings = new HashSet<>();

        /*--------------------
         *  Validate Request
        --------------------*/
        if (year < Year.now().getValue()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "year must be greater than or equal to the current year");
        }

        if (course.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "course not found with this code " + dto.courseCode());
        }
        if (professor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "professor not found with this username " + dto.adminProfessor());
        }

        // check Timings
        Set<ClassType> typeUsed = new HashSet<>();
        dto.timings().forEach(timingDto -> {
            Optional<Location> location = locationRepository.findById(timingDto.locationId());

//            log.info(timingDto);
            log.info("size: {}", typeUsed);
            log.info("contain {}", typeUsed.contains(timingDto.type()));
            log.info("type {}", timingDto.type());

            if (typeUsed.contains(timingDto.type())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Class types must be distinct.");
            } else {
                typeUsed.add(timingDto.type());
            }


            if (location.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid location id");
            }

            if (timingDto.startTime().toLocalTime().getSecond() != 0 ||
                    timingDto.endTime().toLocalTime().getSecond() != 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "seconds must be zero");

            }
            if (timingDto.startTime().after(timingDto.endTime()) || timingDto.startTime().equals(timingDto.endTime())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "start time must be before end time");
            }
            if (timingDto.startTime().before(Time.valueOf("08:00:00")) || timingDto.endTime().after(Time.valueOf("22:00:00"))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "start time must be between 08:00 and 22:00");
            }


            CourseClassTiming timingObj = CourseClassTiming.builder()
                    .day(timingDto.day())
                    .startTime(timingDto.startTime())
                    .endTime(timingDto.endTime())
                    .location(location.get())
                    .type(timingDto.type())
                    .build();

            if (!isValidTiming(timingObj, course.get())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Time: Location Has Another course overlap in this time");
            }
            timings.add(timingObj);
        });


        int groupNumber = courseClassRepository.countDistinctByCourseAndYearAndSemester(course.get(), year, dto.semester()) + 1;

        CourseClass courseClass = courseClassRepository.save(CourseClass.builder()
                .semester(dto.semester())
                .maxCapacity(dto.maxCapacity())
                .course(course.get())
                .adminProfessor(professor.get())
                .year(year)
                .state(state)
                .groupNumber(groupNumber)
                .build());

        // save course class timings
        timings.forEach(t -> {
            t.setCourseClass(courseClass);
            courseClassTimingRepository.save(t);
        });

        CourseClassDto courseClassDtoResponse =  courseClassMapper.mapTo(courseClass);
        courseClassDtoResponse.setCourseCode(course.get().getCode());
        return  courseClassDtoResponse;

    }


    @Override
    public Collection<CourseClassDto> getAllCourseClasses() {

        Collection<CourseClass> courseClasses =  courseClassRepository.findAll() ;
        if(courseClasses.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course classes not found");
        }

        return courseClasses.stream().map(courseClassMapper::mapTo).toList() ;
    }


    @Override
    public CourseClassDto getCourseClass(String courseCode , Integer groupNumber) {
        //check if the course exist
        Optional<Course> course = courseRepository.findByCodeIgnoreCase(courseCode);
        if (course.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course not found with this code " + courseCode);
        }

        Optional<CourseClass> courseClass = courseClassRepository.findByCourseIdAndGroupNumber(course.get().getId(), groupNumber);
        if (courseClass.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this group number " + groupNumber);
        }

        return courseClassMapper.mapTo(courseClass.get());
    }


    @Override
    public MessageResponse deleteCourseClass(String courseCode , Integer groupNumber) {
        //check if the course exist
        Optional<Course> course = courseRepository.findByCodeIgnoreCase(courseCode);
        if (course.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course not found with this code " + courseCode);
        }

        Optional<CourseClass> courseClass = courseClassRepository.findByCourseIdAndGroupNumber(course.get().getId(), groupNumber);
        if (courseClass.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this group number " + groupNumber);
        }

        courseClassRepository.delete(courseClass.get());

        return new MessageResponse("course class deleted successfully");
    }


    @Override
    public CourseClassDto updateCourseClass(String courseCode, Integer groupNumber, CreateCourseClassDto courseClassDto) {

        Optional<Course> course = courseRepository.findByCodeIgnoreCase(courseCode);
        if (course.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course not found with this code " + courseCode);
        }

        Optional<CourseClass> courseClass = courseClassRepository.findByCourseIdAndGroupNumber(course.get().getId(), groupNumber);
        if (courseClass.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this group number " + groupNumber);
        }

        CourseClass courseClassUpdating = createCourseClassMapper.update(courseClassDto, courseClass.get());

        courseClassRepository.save(courseClassUpdating);

        return courseClassMapper.mapTo(courseClassUpdating);


    }


    @Override
    public Collection<CourseClassDto> getCourseClassesByCourseCodeAndSemester(String courseCode, YearSemester semester, Integer Year) {

        Optional<Course> course = courseRepository.findByCodeIgnoreCase(courseCode);
        if (course.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course not found with this code " + courseCode);
        }

        Collection<CourseClass> courseClasses = courseClassRepository.findByCourseIdAndSemesterAndYear(course.get().getId(), semester, Year);
        if(courseClasses.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course classes not found with this course code " + courseCode + " and semester " + semester + "and year " + Year);
        }
        System.out.println(courseClasses);

        return  courseClasses.stream().map(courseClassMapper::mapTo).toList() ;
    }


    private boolean isValidTiming(CourseClassTiming timing, Course course) {
        Set<CourseClass> courseClasses = courseClassRepository
                .findByStateInAndTimings_LocationAndTimings_DayAndTimings_StartTimeBeforeAndTimings_EndTimeAfter(
                        Set.of(CourseClassState.REGISTRATION, CourseClassState.IN_PROGRESS),
                        timing.getLocation(),
                        timing.getDay(),
                        timing.getEndTime(),
                        timing.getStartTime()
                );

        return courseClasses.stream().allMatch(
                courseClass -> courseClass.getCourse().equals(course)
        );
    }
}
