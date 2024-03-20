package com.cu.sci.lambdaserver.coursePrerequisties;


import com.cu.sci.lambdaserver.course.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursePrerequisitesService implements ICoursePrerequisites {
    private final CoursePrerequisitesRepository coursePrerequisitesRepository;

    public void addDependencies(CoursePrerequisites coursePrerequisites) {
        Course prerequisite = coursePrerequisites.getPrerequisite();
        Course course = coursePrerequisites.getCourse();
        System.out.println(course);
        System.out.println(prerequisite);
        List<Long> l = findDependenciesForCourse(prerequisite.getId());
        if (l.contains(course.getId()))
            throw new IllegalStateException("this would make a cycle ( ambiguous dependencies ) ");
        System.out.println(coursePrerequisites);
        coursePrerequisitesRepository.save(coursePrerequisites);
    }

    public List<Long> findDependenciesForCourse(Long id) {
        return coursePrerequisitesRepository.findDependenciesForCourse(id);
    }
}
