package com.cu.sci.lambdaserver.course.service;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.course.dto.CreateCourseRequestDto;
import com.cu.sci.lambdaserver.course.dto.DepartmentCoursesCollectingDto;
import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.course.entites.DepartmentCourses;
import com.cu.sci.lambdaserver.course.entites.DepartmentCoursesKey;
import com.cu.sci.lambdaserver.course.repositries.CourseRepository;
import com.cu.sci.lambdaserver.course.repositries.DepartmentCoursesRepository;
import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.department.DepartmentRepository;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.DepartmentSemester;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService implements ICourseService {
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final DepartmentCoursesRepository departmentCoursesRepository;
    private final IMapper<Course, CourseDto> courseMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public CourseDto createCourse(CreateCourseRequestDto createCourseDto) {
        //check if the course code is already exist
        boolean courseWithSameCode = courseRepository.existsByCodeIgnoreCase(createCourseDto.getCode());
        if (courseWithSameCode) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, " this code already exist " + createCourseDto.getCode());
        }

        //check if the department code if exist in department table
        HashSet<String> usedDepartment = new HashSet<>();
        createCourseDto.getDepartments().forEach(department -> {
            boolean departmentIsExist = departmentRepository.existsByCodeIgnoreCase(department.departmentCode());
            if (!departmentIsExist) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "this department code doesn't exist " + department.departmentCode());
            }
            if (usedDepartment.contains(department.departmentCode()))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Can't repeat department (" + department.departmentCode() + ")");
            usedDepartment.add(department.departmentCode());
        });

        // check for prerequisites and get them
        HashSet<String> usedCourses = new HashSet<>();
        Set<Course> prerequisites =
                Optional.ofNullable(createCourseDto.getCoursePrerequisites()).
                        orElse(Collections.emptyList()).stream()
                        .map(code -> {
                            if (usedCourses.contains(code))
                                throw new ResponseStatusException(HttpStatus.CONFLICT, "Can't Repeat Prerequisites (" + code + ")");

                            usedCourses.add(code);
                            return courseRepository
                                    .findByCodeIgnoreCase(code)
                                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Prerequisites error not found Course code " + code));
                        }).collect(Collectors.toSet());


        //create course and save it in course table
        Course courseSaved = courseRepository.save(Course.builder()
                .name(createCourseDto.getName())
                .code(createCourseDto.getCode())
                .info(createCourseDto.getInfo())
                .creditHours(createCourseDto.getCreditHours())
                .coursePrerequisites(prerequisites)
                .departments(Collections.emptySet())
                .build());


        //create departmentCourses and save it in department_courses table for each department
        createCourseDto.getDepartments().forEach(departmentDto -> {
            Department department = departmentRepository
                    .findDepartmentByCodeIgnoreCase(departmentDto.departmentCode())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Department code not found: " + departmentDto.departmentCode()
                    ));


            DepartmentCourses departmentCourses = DepartmentCourses.builder()
                    .departmentCoursesKey(new DepartmentCoursesKey(department.getId(), courseSaved.getId()))
                    .course(courseSaved)
                    .department(department)
                    .semester(departmentDto.semester())
                    .mandatory(departmentDto.mandatory())
                    .build();
            departmentCoursesRepository.save(departmentCourses);
        });

        return courseMapper.mapTo(courseSaved);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CourseDto getCourseByCode(String courseCode) {
        Course course = courseRepository.findByCodeIgnoreCase(courseCode)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "the course with code : " + courseCode + " doesn't exist")
                );

        return courseMapper.mapTo(course);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CourseDto> getCourses(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Course> coursePage = courseRepository.findAll(pageable);

        return coursePage.map(courseMapper::mapTo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageResponse deleteCourseByCode(String courseCode) {
        Course course = courseRepository.findByCodeIgnoreCase(courseCode)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "the course with code : " + courseCode + " doesn't exist ")
                );

        courseRepository.delete(course);
        return new MessageResponse("Course deleted successfully.");
    }

    public Set<CourseDto> search(String q) {
        if (q == null || q.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Query string is required");

        Pageable pageable = PageRequest.of(0, 5);
        Page<Course> courses = courseRepository.findDistinctByNameLikeIgnoreCaseOrCodeLikeIgnoreCase(q, q, pageable);
        return courses.getContent().stream().map(courseMapper::mapTo).collect(Collectors.toSet());
    }

    @Override
    public Course updateCourse(String courseCode, Course course) {
        return courseRepository.findByCodeIgnoreCase(courseCode).map(currentCourse -> {
            Optional.ofNullable(course.getName()).ifPresent(currentCourse::setName);
            Optional.ofNullable(course.getCreditHours()).ifPresent(currentCourse::setCreditHours);
            Optional.ofNullable(course.getDepartments()).ifPresent(currentCourse::setDepartments);
            Optional.ofNullable(course.getInfo()).ifPresent(currentCourse::setInfo);
            Optional<Course> courseWithSameCode = courseRepository.findByCodeIgnoreCase(course.getCode());
            if (courseWithSameCode.isPresent() && !Objects.equals(courseWithSameCode.get().getCode(), courseCode))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "this code already exist " + course.getCode());
            else
                Optional.ofNullable(course.getCode()).ifPresent(currentCourse::setCode);
            return courseRepository.save(currentCourse);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the course with code : " + courseCode + " doesn't exist "));
    }

    public Course addPrerequisite(String courseCode, String prerequisiteCode) {
        if (courseCode.equals(prerequisiteCode))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "the course can't be a prerequisite for itself");

        Optional<Course> course = courseRepository.findByCodeIgnoreCase(courseCode);
        Optional<Course> prerequisite = courseRepository.findByCodeIgnoreCase(prerequisiteCode);

        if (course.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " the course with code : " + courseCode + " not found ");

        if (prerequisite.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " the course with code : " + prerequisiteCode + " not found ");

        Collection<Course> prerequisitesOfPrerequisite = getAllPrerequisites(prerequisite.get().getCode());

        if (prerequisitesOfPrerequisite.contains(course.get()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "this would make a cycle ( ambiguous dependencies ) ");

        Set<Course> newPrerequisites = getPrerequisite(course.get().getCode());
        if (newPrerequisites == null)
            newPrerequisites = new HashSet<>();
        newPrerequisites.add(prerequisite.get());
        course.get().setCoursePrerequisites(newPrerequisites);

        return courseRepository.save(course.get());
    }

    public Set<Course> getPrerequisite(String courseCode) {
        Optional<Course> course = courseRepository.findByCodeIgnoreCase(courseCode);
        if (course.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " the course with code : " + courseCode + " not found ");
        return course.get().getCoursePrerequisites();
    }

    public Collection<Course> getAllPrerequisites(String courseCode) {
        Optional<Course> course = courseRepository.findByCodeIgnoreCase(courseCode);
        if (course.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " the course with code : " + courseCode + " not found ");
        Collection<Course> prerequisites = course.get().getCoursePrerequisites();
        Queue<Course> queue = new LinkedList<>(prerequisites);
        while (!queue.isEmpty()) {
            prerequisites.addAll(Objects.requireNonNull(queue.peek()).getCoursePrerequisites());
            queue.addAll(Objects.requireNonNull(queue.poll()).getCoursePrerequisites());
        }
        return prerequisites;
    }

    @Override
    public Course removePrerequisite(String courseCode, String prerequisiteCode) {
        Optional<Course> course = courseRepository.findByCodeIgnoreCase(courseCode);
        Optional<Course> prerequisite = courseRepository.findByCodeIgnoreCase(prerequisiteCode);

        if (course.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " the course with code : " + courseCode + " not found ");

        if (prerequisite.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " the course with code : " + prerequisiteCode + " not found ");

        Set<Course> newPrerequisites = getPrerequisite(course.get().getCode());
        if (!newPrerequisites.contains(prerequisite.get()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " the course with code : " + courseCode + " doesn't have the prerequisite with code : " + prerequisiteCode + ")");

        newPrerequisites.remove(prerequisite.get());
        course.get().setCoursePrerequisites(newPrerequisites);

        return courseRepository.save(course.get());
    }

    @Override
    public DepartmentCoursesCollectingDto changeMandatoryAndSemester(String courseCode, String departmentCode, Boolean mandatory, String semester) {
        Optional<DepartmentCourses> departmentCourses = departmentCoursesRepository.findByCourseCodeAndDepartmentCode(courseCode, departmentCode);
        if (departmentCourses.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "the course with code : " + courseCode + "doesn't exist in department code : " + departmentCode);
        departmentCourses.get().setMandatory(mandatory);
        departmentCourses.get().setSemester(DepartmentSemester.valueOf(semester));
        departmentCoursesRepository.save(departmentCourses.get());
        return DepartmentCoursesCollectingDto.builder()
                .code(courseCode)
                .departmentCode(new ArrayList<>(Collections.singletonList(departmentCode)))
                .semester(DepartmentSemester.valueOf(semester))
                .mandatory(mandatory)
                .build();
    }
}
