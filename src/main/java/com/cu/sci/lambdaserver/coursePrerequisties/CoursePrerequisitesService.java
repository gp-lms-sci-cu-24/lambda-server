package com.cu.sci.lambdaserver.coursePrerequisties;


import com.cu.sci.lambdaserver.course.repositries.CourseRepository;
import com.cu.sci.lambdaserver.course.entites.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoursePrerequisitesService implements ICoursePrerequisitesService {
    private final CoursePrerequisitesRepository coursePrerequisitesRepository;
    private final CourseRepository courseRepository;

    public CoursePrerequisites addPrerequisite(Long courseId, Long prerequisiteId) {
        Optional<Course> course = courseRepository.findById(courseId);
        Optional<Course> prerequisite = courseRepository.findById(prerequisiteId);

        if (course.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " the course with id : " + courseId + " not found ");

        if (prerequisite.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " the course with id : " + prerequisiteId + " not found ");

        List<Long> l = getPrerequisite(prerequisite.get().getId());

        if (l.contains(course.get().getId()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "this would make a cycle ( ambiguous dependencies ) ");

        return coursePrerequisitesRepository.save(CoursePrerequisites.builder().course(course.get()).prerequisite(prerequisite.get()).build());
    }

    public List<Long> getPrerequisite(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " the course with id : " + id + " not found ");

        return coursePrerequisitesRepository.getPrerequisite(id);
    }
}
