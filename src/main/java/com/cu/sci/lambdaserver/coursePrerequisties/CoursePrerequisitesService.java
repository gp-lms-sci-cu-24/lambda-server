package com.cu.sci.lambdaserver.coursePrerequisties;


import com.cu.sci.lambdaserver.course.entites.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursePrerequisitesService implements ICoursePrerequisites {
    private final CoursePrerequisitesRepository coursePrerequisitesRepository;

    public void createPrerequisite(CoursePrerequisites coursePrerequisites) {
        Course prerequisite = coursePrerequisites.getPrerequisite();
        Course course = coursePrerequisites.getCourse();
        List<Long> l = getPrerequisite(prerequisite.getId());
        if (l.contains(course.getId()))
            throw new IllegalStateException("this would make a cycle ( ambiguous dependencies ) ");
        coursePrerequisitesRepository.save(coursePrerequisites);
    }

    public List<Long> getPrerequisite(Long id) {
        return coursePrerequisitesRepository.getPrerequisite(id);
    }
}
