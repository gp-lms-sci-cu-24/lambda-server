package com.cu.sci.lambdaserver.coursePrerequisties;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursePrerequisitesRepository extends JpaRepository<CoursePrerequisites, Long> {
    @Query(value = "WITH RECURSIVE course_tree AS ( "
            + " SELECT cd.course_Id, cd.prerequisite_Id "
            + " FROM CoursePrerequisites cd "
            + " WHERE cd.course_Id = :courseId "
            + " UNION ALL "
            + " SELECT cd.course_Id, cd.prerequisite_Id "
            + " FROM CoursePrerequisites cd "
            + " JOIN course_tree ct ON cd.course_Id = ct.prerequisite_Id "
            + ")"
            + " SELECT DISTINCT prerequisite_Id"
            + " FROM course_tree", nativeQuery = true)
    List<Long> findDependenciesForCourse(Long courseId);
}
