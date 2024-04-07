package com.cu.sci.lambdaserver.courseclass.service;


import com.cu.sci.lambdaserver.classGroup.dto.ClassGroupDto;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.mapper.CourseClassMapper;
import com.cu.sci.lambdaserver.utils.enums.Semester;
import com.cu.sci.lambdaserver.classGroup.CourseClassGroup;
import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.course.service.CourseService;
import com.cu.sci.lambdaserver.courseclass.CourseClass;
import com.cu.sci.lambdaserver.courseclass.CourseClassRepository;
import com.cu.sci.lambdaserver.utils.enums.State;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseClassService implements ICourseClassService {
    private final CourseClassRepository courseClassRepository;
    private final CourseClassMapper courseClassMapper;
    private final CourseService courseService;
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
    public CourseClass updateCourseClass(CourseClassDto courseClassDto) {
        // make sure that the courseClass exist
        Long id = courseClassDto.getCourseClassId();
        CourseClass courseClass = courseClassRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this id") );
        courseClassMapper.update(courseClassDto,courseClass );
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

}
