package com.cu.sci.lambdaserver.courseclass.service;


import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.course.repositries.CourseRepository;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassResponseDto;
import com.cu.sci.lambdaserver.courseclass.dto.CreateCourseClassRequestDto;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClassTiming;
import com.cu.sci.lambdaserver.courseclass.mapper.CourseClassMapper;
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
import com.cu.sci.lambdaserver.utils.helpers.CollectionHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Time;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseClassService implements ICourseClassService {

    private final CourseClassMapper courseClassMapper;

    // repostiories
    private final CourseClassRepository courseClassRepository;
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;
    private final LocationRepository locationRepository;
    private final CourseClassTimingRepository courseClassTimingRepository;


    @Override
    public CourseClassResponseDto createCourseClass(CreateCourseClassRequestDto dto) {

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

        return courseClassMapper.mapTo(courseClass);
    }


    @Override
    public Page<CourseClassResponseDto> getAllCourseClasses(
            Integer pageNo,
            Integer pageSize,
            Set<CourseClassMapper.Include> include,
            Set<CourseClassState> state,
            Set<YearSemester> semesters,
            Set<Integer> years,
            String professorUsername
    ) {

        final Set<CourseClassState> finalState = CollectionHelper.clearNullsSet(state);
        final Set<CourseClassMapper.Include> finalInclude = CollectionHelper.clearNullsSet(include);
        final Set<YearSemester> finalSemesters = CollectionHelper.clearNullsSet(semesters);
        final Set<Integer> finalYears = CollectionHelper.clearNullsSet(years);

        Pageable pageable = PageRequest.of(pageNo, pageSize, defultSort());
        Page<CourseClass> courseClasses;

        if (!finalYears.isEmpty() && !professorUsername.isEmpty()) {
            courseClasses = courseClassRepository.findAllByStateInAndSemesterInAndYearInAndAdminProfessorUsernameIgnoreCase(pageable, finalState, finalSemesters, finalYears, professorUsername);
        } else if (!finalYears.isEmpty()) {
            courseClasses = courseClassRepository.findAllByStateInAndSemesterInAndYearIn(pageable, finalState, finalSemesters, finalYears);
        } else if (!professorUsername.isEmpty()) {
            courseClasses = courseClassRepository.findAllByStateInAndSemesterInAndAdminProfessorUsernameIgnoreCase(pageable, finalState, finalSemesters, professorUsername);
        } else {
            courseClasses = courseClassRepository.findAllByStateInAndSemesterIn(pageable, finalState, finalSemesters);
        }

        return courseClasses.map((c) -> courseClassMapper.mapTo(c, finalInclude));

    }


    @Override
    public Page<CourseClassResponseDto> getCourseClassByCourse(Integer pageNo, Integer pageSize, String courseCode, Set<CourseClassMapper.Include> include, Set<CourseClassState> state, Set<YearSemester> semesters, Set<Integer> years, String professorUsername) {
        boolean courseFound = courseRepository.existsByCodeIgnoreCase(courseCode);
        if (!courseFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course not found with this code " + courseCode);
        }

        final Set<CourseClassState> finalState = CollectionHelper.clearNullsSet(state);
        final Set<CourseClassMapper.Include> finalInclude = CollectionHelper.clearNullsSet(include);
        final Set<YearSemester> finalSemesters = CollectionHelper.clearNullsSet(semesters);
        final Set<Integer> finalYears = CollectionHelper.clearNullsSet(years);


        Pageable pageable = PageRequest.of(pageNo, pageSize, defultSort());
        Page<CourseClass> courseClasses;

        if (!finalYears.isEmpty() && !professorUsername.isEmpty()) {
            courseClasses = courseClassRepository.findAllByStateInAndSemesterInAndYearInAndCourseCodeIsAndAdminProfessorUsernameIgnoreCase(
                    pageable, finalState, finalSemesters, finalYears, courseCode, professorUsername
            );
        } else if (!finalYears.isEmpty()) {
            courseClasses = courseClassRepository.findAllByStateInAndSemesterInAndYearInAndCourseCodeIs(pageable, finalState, finalSemesters, finalYears, courseCode);
        } else if (!professorUsername.isEmpty()) {
            courseClasses = courseClassRepository.findAllByStateInAndSemesterInAndCourseCodeIsAndAdminProfessorUsernameIgnoreCase(pageable, finalState, finalSemesters, courseCode, professorUsername);
        } else {
            courseClasses = courseClassRepository.findAllByStateInAndSemesterInAndCourseCodeIs(pageable, finalState, finalSemesters, courseCode);
        }

        return courseClasses.map((c) -> courseClassMapper.mapTo(c, finalInclude));
    }



    @Override
    public CourseClassResponseDto getCourseClassByCourseAndYearAndSemesterAndGroup(String courseCode, Integer years, YearSemester semesters, Integer groupNumber) {
        boolean courseFound = courseRepository.existsByCodeIgnoreCase(courseCode);
        if (!courseFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course not found with this code " + courseCode);
        }

        Optional<CourseClass> courseClass = courseClassRepository.findBySemesterIsAndYearIsAndCourseCodeIsAndGroupNumberIs(semesters, years, courseCode, groupNumber);
        if (courseClass.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this group number " + groupNumber);
        }

        return courseClassMapper.mapTo(courseClass.get());
    }
    @Override
    public MessageResponse deleteCourseClassByCourseAndYearAndSemesterAndGroup(String courseCode, Integer years, YearSemester semesters, Integer groupNumber) {
        Optional<Course> course = courseRepository.findByCodeIgnoreCase(courseCode);
        if (course.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course not found with this code " + courseCode);
        }

        Optional<CourseClass> courseClass = courseClassRepository.findBySemesterIsAndYearIsAndCourseCodeIsAndGroupNumberIs(semesters, years, courseCode, groupNumber);
        if (courseClass.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this group number " + groupNumber);
        }

        courseClassRepository.delete(courseClass.get());
        Set<CourseClass> classes = courseClassRepository.findAllByCourseAndYearAndSemester(course.get(), years, semesters, defultSort());
        AtomicInteger group = new AtomicInteger(0);
        classes.forEach(el -> {
            el.setGroupNumber(group.incrementAndGet());
            courseClassRepository.save(el);
        });

        return new MessageResponse("course class deleted successfully");
    }

//    @Override
//    public CourseClassResponseDto getCourseClass(String courseCode , Integer groupNumber) {
//        //check if the course exist
//        Optional<Course> course = courseRepository.findByCodeIgnoreCase(courseCode);
//        if (course.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course not found with this code " + courseCode);
//        }
//
//        Optional<CourseClass> courseClass = courseClassRepository.findByCourseIdAndGroupNumber(course.get().getId(), groupNumber);
//        if (courseClass.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this group number " + groupNumber);
//        }
//
//        return courseClassMapper.mapTo(courseClass.get());
//    }


//    @Override
//    public CourseClassResponseDto updateCourseClass(String courseCode, Integer groupNumber, CreateCourseClassDto courseClassDto) {
//
////        Optional<Course> course = courseRepository.findByCodeIgnoreCase(courseCode);
////        if (course.isEmpty()) {
////            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course not found with this code " + courseCode);
////        }
////
////        Optional<CourseClass> courseClass = courseClassRepository.findByCourseIdAndGroupNumber(course.get().getId(), groupNumber);
////        if (courseClass.isEmpty()) {
////            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this group number " + groupNumber);
////        }
////
////        CourseClass courseClassUpdating = createCourseClassMapper.update(courseClassDto, courseClass.get());
////
////        courseClassRepository.save(courseClassUpdating);
////
////        return courseClassMapper.mapTo(courseClassUpdating);
//
//        throw new UnsupportedOperationException("Update not supported yet");
//
//    }


//    @Override
//    public Collection<CourseClassResponseDto> getCourseClassesByCourseCodeAndSemester(String courseCode, YearSemester semester, Integer Year) {
//
//        Optional<Course> course = courseRepository.findByCodeIgnoreCase(courseCode);
//        if (course.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course not found with this code " + courseCode);
//        }
//
//        Collection<CourseClass> courseClasses = courseClassRepository.findByCourseIdAndSemesterAndYear(course.get().getId(), semester, Year);
//        if(courseClasses.isEmpty()){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course classes not found with this course code " + courseCode + " and semester " + semester + "and year " + Year);
//        }
//        System.out.println(courseClasses);
//
//        return  courseClasses.stream().map(courseClassMapper::mapTo).toList() ;
//    }



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

    private Sort defultSort() {
        return Sort.by(List.of(
                Sort.Order.desc("year"),
                Sort.Order.desc("semester"),
                Sort.Order.by("course.code"),
                Sort.Order.asc("groupNumber")
        ));
    }
}
