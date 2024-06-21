package com.cu.sci.lambdaserver.courseregister.repositories;

import com.cu.sci.lambdaserver.courseregister.entities.CourseRegisterLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRegisterLogRepository extends JpaRepository<CourseRegisterLog, Long> {
}