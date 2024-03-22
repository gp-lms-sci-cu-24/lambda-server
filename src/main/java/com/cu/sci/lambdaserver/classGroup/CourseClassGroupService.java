package com.cu.sci.lambdaserver.classGroup;

import com.cu.sci.lambdaserver.classGroup.dto.ClassGroupDto;
import com.cu.sci.lambdaserver.courseClass.CourseClass;
import com.cu.sci.lambdaserver.courseClass.service.CourseClassService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseClassGroupService {
    private CourseClassGroupRepository courseClassGroupRepository;
    private CourseClassService courseClassService;
    public CourseClassGroupService(CourseClassGroupRepository courseClassGroupRepository, CourseClassService courseClassService) {
        this.courseClassGroupRepository = courseClassGroupRepository;
        this.courseClassService = courseClassService;
    }
    public boolean canAddGroup(ClassGroupDto classGroupDto, CourseClass courseClass, CourseClassGroup classGroup){
        Integer capacity = classGroupDto.getMaxCapacity();
        Boolean exact = classGroupDto.getIsExact();

        if(exact && courseClass.getCapacitySoFar() + capacity > courseClass.getMaxCapacity() ){
            return false;
        }
        Integer actualCapacity=Math.min(capacity, courseClass.getMaxCapacity() - courseClass.getCapacitySoFar() );
        if(actualCapacity == 0){
            return false;
        }
        return true;
    }
    public boolean addGroup(ClassGroupDto classGroupDto, CourseClass courseClass, CourseClassGroup classGroup){
        if(!canAddGroup(classGroupDto, courseClass, classGroup) )
            return false;
        Integer capacity = classGroupDto.getMaxCapacity();
        Integer actualCapacity=Math.min(capacity, courseClass.getMaxCapacity() - courseClass.getCapacitySoFar() );

        // if this is a new group, get it a group number
        if(classGroup.getGroupNumber() == null){
            classGroup.setGroupNumber(courseClass.getGroupNumberSeq() + 1 );
            courseClass.setGroupNumberSeq(courseClass.getGroupNumberSeq() + 1);
        }
        classGroup.setCourseClass(courseClass);
        classGroup.setMaxCapacity(actualCapacity);

        courseClass.setCapacitySoFar(courseClass.getCapacitySoFar() + actualCapacity);

        return true;
    }
    public boolean canDelete(ClassGroupDto classGroupDto, CourseClass courseClass, CourseClassGroup classGroup){
        return true;
    }
    public boolean deleteGroup(ClassGroupDto classGroupDto, CourseClass courseClass, CourseClassGroup classGroup){
        if(!canDelete(classGroupDto, courseClass, classGroup) ){
            return false;
        }
        courseClass.setNumberOfStudentsRegistered(courseClass.getNumberOfStudentsRegistered()
                - classGroup.getNumberOfStudentsRegistered() );
        courseClass.setCapacitySoFar(courseClass.getCapacitySoFar()
                - classGroup.getMaxCapacity() );
        return true;
    }
    // need to update properly later
    public boolean canUpdate(ClassGroupDto classGroupDto, CourseClass courseClass, CourseClassGroup classGroup){
        return true;
    }
    public boolean updateGroup(ClassGroupDto classGroupDto, CourseClass courseClass, CourseClassGroup classGroup){
        if(!deleteGroup(classGroupDto,courseClass,classGroup) ) return false;
        if(!addGroup(classGroupDto,courseClass,classGroup) ) return false;
        return true;
    }
    @Transactional
    public CourseClassGroup createCourseClassGroup(ClassGroupDto classGroupDto){
        // what should i return if i couldnt save group?
        Long courseClassId = classGroupDto.getCourseClassId();
        return courseClassService.getCourseClassById(courseClassId)
            .map((courseClass) -> {
                CourseClassGroup classGroup = new CourseClassGroup();
                if(!addGroup(classGroupDto, courseClass, classGroup) ){
                    return null;
                }
                // save both course and group
                courseClassService.saveCourseClass(courseClass);
                return courseClassGroupRepository.save(classGroup);
            })
            .orElse(null );
    }
    @Transactional
    public void deleteCourseClassGroup(ClassGroupDto classGroupDto){
        // what should i return if i couldnt save group?
//        Long classId = classGroupDto.getCourseClassId();
        Long classGroupId = classGroupDto.getClassGroupId();
        getCourseClassGroupById(classGroupId).ifPresent(classGroup->{
            if(!deleteGroup(classGroupDto, classGroup.getCourseClass(), classGroup) ) return;
            courseClassGroupRepository.deleteById(classGroupId);
        });
//        courseClassService.getCourseClassById(classId).ifPresent(courseClass -> {
//            getCourseClassGroupById(classGroupId).ifPresent(classGroup -> {
//                if (!deleteGroup(classGroupDto, courseClass, classGroup) ) return;
//                courseClassGroupRepository.deleteById(classGroupId);
//            });
//        });
    }
    @Transactional
    public CourseClassGroup updateCourseClassGroup(ClassGroupDto classGroupDto){
        // what should i return if i couldnt save group?
//        Long courseClassId = classGroupDto.getCourseClassId();
        Long classGroupId = classGroupDto.getClassGroupId();
        return  getCourseClassGroupById(classGroupId).map((classGroup)->{
            if(!updateGroup(classGroupDto, classGroup.getCourseClass(), classGroup) ){
                return null;
            }
            courseClassService.saveCourseClass(classGroup.getCourseClass() );
            return courseClassGroupRepository.save(classGroup);
        }).orElse(null);
//        return courseClassService.getCourseClassById(courseClassId)
//            .map((courseClass) -> {
//                return getCourseClassGroupById(classGroupId).map((classGroup) ->{
//                    if(!updateGroup(classGroupDto, courseClass, classGroup) ){
//                        return null;
//                    }
//                    courseClassService.saveCourseClass(courseClass);
//                    return courseClassGroupRepository.save(classGroup);
//                }).orElse(null);
//            })
//            .orElse(null );
    }
    public List<CourseClassGroup> getAllCourseClassGroups() {
        return courseClassGroupRepository.findAll();
    }

    public Optional<CourseClassGroup> getCourseClassGroupById(Long id) {
        return courseClassGroupRepository.findById(id);
    }

    public CourseClassGroup saveCourseClassGroup(CourseClassGroup courseClassGroup) {
        return courseClassGroupRepository.save(courseClassGroup);
    }

    public void deleteCourseClassGroupById(Long id) {
        courseClassGroupRepository.deleteById(id);
    }
    public void init() {
//        CourseClass courseClass1 = courseClassService.getCourseClassById(1L).get();
//        CourseClass courseClass2 = courseClassService.getCourseClassById(2L).get();
//
//        List<CourseClassGroup> groups = new ArrayList<>();
//        groups.add(CourseClassGroup.builder()
//                .courseClass(courseClass1)
//                .maxCapacity(20)
//                .build());
//        groups.add(CourseClassGroup.builder()
//                .courseClass(courseClass1)
//                .maxCapacity(15)
//                .build());
//
//        courseClassGroupRepository.saveAll(groups);
//        System.out.println("printing all course groups in db on start of application");
//        courseClassGroupRepository.findAll().forEach(user -> {
//            System.out.println(user.toString() );
//        });
    }
}
