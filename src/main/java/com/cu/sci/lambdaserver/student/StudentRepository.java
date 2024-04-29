package com.cu.sci.lambdaserver.student;

import com.cu.sci.lambdaserver.user.UserAbstractionRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends UserAbstractionRepository<Student> {
    Optional<Student> findByCode(String code);
    Collection<Student> getByCodeIn(List<String> studentsCode);
}
