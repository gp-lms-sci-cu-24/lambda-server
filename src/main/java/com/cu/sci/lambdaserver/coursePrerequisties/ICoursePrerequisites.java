package com.cu.sci.lambdaserver.coursePrerequisties;

import java.util.List;

public interface ICoursePrerequisites {
    void addDependencies(CoursePrerequisites coursePrerequisites);

    List<Long> findDependenciesForCourse(Long id);
}
