package com.cu.sci.lambdaserver.courseClass.service;

import com.cu.sci.lambdaserver.course.Course;
import com.cu.sci.lambdaserver.course.CourseService;
import com.cu.sci.lambdaserver.classGroup.dto.ClassGroupDto;
import com.cu.sci.lambdaserver.classGroup.CourseClassGroup;
import com.cu.sci.lambdaserver.courseClass.CourseClass;
import com.cu.sci.lambdaserver.courseClass.CourseClassRepository;
import com.cu.sci.lambdaserver.courseClass.Semester;
import com.cu.sci.lambdaserver.utils.enums.State;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseClassService implements iCourseClassService{
    private CourseClassRepository courseClassRepository;
    private CourseService courseService;
    public CourseClassService(CourseClassRepository courseClassRepository, CourseService courseService) {
        this.courseClassRepository = courseClassRepository;
        this.courseService = courseService;
    }
    @Override
    public CourseClass createCourseClass(CourseClass courseClass) {
        return courseClassRepository.save(courseClass);
    }

    @Override
    public List<CourseClass> getAllCourseClasses() {
        return courseClassRepository.findAll();
    }

    @Override
    public Optional<CourseClass> getCourseClassById(Long id) {
        return courseClassRepository.findById(id);
    }

    @Override
    public boolean isCourseClassExists(Long id) {
        return courseClassRepository.existsById(id);
    }
    @Override
    public CourseClass updateCourseClass(Long id, CourseClass courseClassDetails) {
        // make sure that the courseClass exist
        CourseClass courseClass = courseClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CourseClass Not Found With This Id " + id) );
        // update how?

        return courseClassRepository.save(courseClass);
    }
    @Override
    public void deleteCourseClass(Long id) {
        courseClassRepository.deleteById(id);
    }


    public CourseClass saveCourseClass(CourseClass courseClass) {
        return courseClassRepository.save(courseClass);
    }
    public Optional<CourseClass> getLatestClassByCourseId(Long id){ return courseClassRepository.getLatestClassByCourseId(id); }

    public boolean addGroup(ClassGroupDto classGroupDto, CourseClass courseClass, CourseClassGroup classGroup){
        Integer capacity = classGroupDto.getMaxCapacity();
        Boolean exact = classGroupDto.getIsExact();

        if(exact && courseClass.getCapacitySoFar() + capacity > courseClass.getMaxCapacity() ){
            return false;
        }
        Integer actualCapacity=Math.min(capacity, courseClass.getMaxCapacity() - courseClass.getCapacitySoFar() );
        if(actualCapacity == 0){
            return false;
        }
        classGroup.setClassGroupId((long) (courseClass.getGroupNumber() + 1) );
        classGroup.setCourseClass(courseClass);
        classGroup.setMaxCapacity(actualCapacity);

        courseClass.setGroupNumber(courseClass.getGroupNumber() + 1);
        courseClass.setCapacitySoFar(courseClass.getCapacitySoFar() + actualCapacity);

        return true;
    }
    public boolean deleteGroup(ClassGroupDto classGroupDto, CourseClass courseClass, CourseClassGroup classGroup){

        courseClass.setNumberOfStudentsRegistered(courseClass.getNumberOfStudentsRegistered()
                - classGroup.getNumberOfStudentsRegistered() );

        courseClass.setCapacitySoFar(courseClass.getCapacitySoFar()
                - classGroup.getMaxCapacity() );

        return true;
    }
    public void init() {
//        Course course1 = courseService.getCourseById(1L).get();
//        Course course2 = courseService.getCourseById(2L).get();
        // Create Course instances
        Course course1 = new Course();
        course1.setId(1L);

        Course course2 = new Course();
        course2.setId(2L);

        CourseClass courseClass1 = new CourseClass();
        courseClass1.setCourse(course1);
        courseClass1.setCourseSemester(Semester.SPRING);
        courseClass1.setCourseState(State.ACTIVE);
        courseClass1.setMaxCapacity(50);

        CourseClass courseClass2 = new CourseClass();
        courseClass2.setCourse(course2);
        courseClass2.setCourseSemester(Semester.SPRING);
        courseClass2.setCourseState(State.INACTIVE);
        courseClass2.setMaxCapacity(40);

        courseClassRepository.saveAll(List.of(courseClass1, courseClass2) );
        System.out.println("printing all course classes in db on start of application");
        courseClassRepository.findAll().forEach(user -> {
            System.out.println(user.toString() );
        });
    }

}
