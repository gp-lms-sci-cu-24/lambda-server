package com.cu.sci.lambdaserver.coursePrerequisties;

import java.util.List;

public interface ICoursePrerequisites {
    void createPrerequisite(CoursePrerequisites coursePrerequisites);

    List<Long> getPrerequisite(Long id);
}
