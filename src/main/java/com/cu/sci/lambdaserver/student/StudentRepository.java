package com.cu.sci.lambdaserver.student;

import org.hibernate.annotations.CollectionIdJdbcTypeCode;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, PagingAndSortingRepository<Student, Long> {
    Optional<Student> findByCode(String code);
    Collection<Student> getByCodeIn(List<String> studentsCode);
}
