package com.cu.sci.lambdaserver.student;

import com.cu.sci.lambdaserver.user.UserAbstractionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends UserAbstractionRepository<Student> {
    Optional<Student> findByCode(String code);
}
