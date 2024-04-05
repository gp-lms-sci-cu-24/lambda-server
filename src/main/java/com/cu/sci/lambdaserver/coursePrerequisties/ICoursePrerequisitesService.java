package com.cu.sci.lambdaserver.coursePrerequisties;

import java.util.List;

public interface ICoursePrerequisitesService {
    CoursePrerequisites addPrerequisite(Long courseId, Long prerequisiteId);

    List<Long> getPrerequisite(Long id);
}
