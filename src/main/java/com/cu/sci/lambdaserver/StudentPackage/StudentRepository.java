package com.cu.sci.lambdaserver.StudentPackage;

import com.cu.sci.lambdaserver.StudentPackage.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
}
