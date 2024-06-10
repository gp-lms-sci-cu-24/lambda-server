package com.cu.sci.lambdaserver.course.service;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.course.dto.CreateCourseDto;
import com.cu.sci.lambdaserver.course.dto.DepartmentCoursesCollectingDto;
import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.course.entites.DepartmentCourses;
import com.cu.sci.lambdaserver.course.entites.DepartmentCoursesKey;
import com.cu.sci.lambdaserver.course.repositries.CourseRepository;
import com.cu.sci.lambdaserver.course.repositries.DepartmentCoursesRepository;
import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.department.DepartmentRepository;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService implements ICourseService {
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final DepartmentCoursesRepository departmentCoursesRepository;
    private final IMapper<Course, CourseDto> courseMapper;

    public Page<CourseDto> getCourses(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Course> coursePage = courseRepository.findAll(pageable);

        //check if list empty
        if (coursePage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return coursePage.map(courseMapper::mapTo);
    }


    public Course getCourse(String courseCode) {
        Optional<Course> optionalCourse = courseRepository.findByCode(courseCode);
        if (optionalCourse.isPresent())
            return optionalCourse.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "the course with code : " + courseCode + " doesn't exist");
    }

    public CourseDto createCourse(CreateCourseDto createCourseDto) {
        //check if the course code is already exist
        Optional<Course> courseWithSameCode = courseRepository.findByCode(createCourseDto.getCode());
        if (courseWithSameCode.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, " this code already exist " + createCourseDto.getCode());
        }

        //check if the department code if exist in department table
        createCourseDto.getDepartmentCode().forEach(departmentCode -> {
            Optional<Department> department = departmentRepository.findDepartmentByCodeIgnoreCase(departmentCode);
            if (department.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "this department code doesn't exist " + departmentCode);
            }
        });


        //get the department object from department table
        List<Department> departmentList = createCourseDto.getDepartmentCode().stream()
                .map(departmentCode -> departmentRepository
                        .findDepartmentByCodeIgnoreCase(departmentCode)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department code not found: " + departmentCode)))
                .toList();


        //create course and save it in course table
        Course course = Course.builder()
                .name(createCourseDto.getName())
                .code(createCourseDto.getCode())
                .info(createCourseDto.getInfo())
                .creditHours(createCourseDto.getCreditHours())
                .build();
        Course courseSaved = courseRepository.save(course);


        //create departmentCourses and save it in department_courses table for each department
        departmentList.forEach(department -> {
            DepartmentCourses departmentCourses = DepartmentCourses.builder()
                    .departmentCoursesKey(new DepartmentCoursesKey(department.getId(), courseSaved.getId()))
                    .course(courseSaved)
                    .department(department)
                    .semester(DepartmentSemester.FIRST_SEMESTER)
                    .mandatory(false)
                    .build();

            departmentCoursesRepository.save(departmentCourses);
        });
        List<Course> coursePrerequisites = createCourseDto.getCoursePrerequisites().stream()
                .map(courseCode -> courseRepository.findByCode(courseCode).orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course code not found: " + courseCode))).toList();

        coursePrerequisites.forEach(coursePrerequisite -> addPrerequisite(course.getCode(), coursePrerequisite.getCode()));

        return courseMapper.mapTo(courseSaved);
    }

    public Course deleteCourse(String courseCode) {
        Optional<Course> optionalCourse = courseRepository.findByCode(courseCode);
        if (optionalCourse.isPresent()) {
            Course DeletedCourse = optionalCourse.get();
            courseRepository.deleteById(optionalCourse.get().getId());
            return DeletedCourse;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "the course with code : " + courseCode + " doesn't exist ");
    }

    @Override
    public Course updateCourse(String courseCode, Course course) {
        return courseRepository.findByCode(courseCode).map(currentCourse -> {
            Optional.ofNullable(course.getName()).ifPresent(currentCourse::setName);
            Optional.ofNullable(course.getCreditHours()).ifPresent(currentCourse::setCreditHours);
            Optional.ofNullable(course.getDepartments()).ifPresent(currentCourse::setDepartments);
            Optional.ofNullable(course.getInfo()).ifPresent(currentCourse::setInfo);
            Optional<Course> courseWithSameCode = courseRepository.findByCode(course.getCode());
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

        Optional<Course> course = courseRepository.findByCode(courseCode);
        Optional<Course> prerequisite = courseRepository.findByCode(prerequisiteCode);

        if (course.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " the course with code : " + courseCode + " not found ");

        if (prerequisite.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " the course with code : " + prerequisiteCode + " not found ");

        Collection<Course> prerequisitesOfPrerequisite = getAllPrerequisites(prerequisite.get().getCode());

        if (prerequisitesOfPrerequisite.contains(course.get()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "this would make a cycle ( ambiguous dependencies ) ");

        Collection<Course> newPrerequisites = getPrerequisite(course.get().getCode());
        if (newPrerequisites == null)
            newPrerequisites = new HashSet<>();
        newPrerequisites.add(prerequisite.get());
        course.get().setCoursePrerequisites(newPrerequisites);

        return courseRepository.save(course.get());
    }

    public Collection<Course> getPrerequisite(String courseCode) {
        Optional<Course> course = courseRepository.findByCode(courseCode);
        if (course.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " the course with code : " + courseCode + " not found ");
        return course.get().getCoursePrerequisites();
    }

    public Collection<Course> getAllPrerequisites(String courseCode) {
        Optional<Course> course = courseRepository.findByCode(courseCode);
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
        Optional<Course> course = courseRepository.findByCode(courseCode);
        Optional<Course> prerequisite = courseRepository.findByCode(prerequisiteCode);

        if (course.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " the course with code : " + courseCode + " not found ");

        if (prerequisite.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " the course with code : " + prerequisiteCode + " not found ");

        Collection<Course> newPrerequisites = getPrerequisite(course.get().getCode());
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
