package com.cu.sci.lambdaserver.department.services;

import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.department.DepartmentRepository;
import com.cu.sci.lambdaserver.student.Student;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService implements iDepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Department createDepartment(Department department) {
        return departmentRepository.save(department) ;
    }

    @Override
    public Page<Department> getAllDepartments(Integer pageNo,Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize) ;
        return departmentRepository.findAll(pageable) ;
    }

    @Override
    public Department getDepartmentByid(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Department with ID " + id + " does not exist")) ;
    }

    @Override
    public Department getDepartmentByname(String name) {
        return departmentRepository.findDepartmentByName(name)
                .orElseThrow(()->new EntityNotFoundException("Department with Name " + name + " does not exist")) ;
    }

    @Override
    public Department updateDepartment(Long id,Department department) {
        if(!departmentRepository.existsById(id)){
            throw new EntityNotFoundException("Department with ID " + id + " does not exist") ;
        }
        return departmentRepository.findById(id).map(exsistingDepartment ->{
            Optional.ofNullable(department.getName()).ifPresent(exsistingDepartment::setName);
            Optional.ofNullable(department.getInfo()).ifPresent(exsistingDepartment::setInfo);
            return departmentRepository.save(exsistingDepartment) ;
        }).orElseThrow(()->new EntityNotFoundException("Department with ID " + id + " does not exist")) ;
    }

    @Override
    public List<Student> getStudentsdepartment(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Department with Id " + id + " does not exist"))
                .getStudents()
                .stream()
                .toList();
    }

    @Override
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Department with ID " + id + " does not exist");
        }
        departmentRepository.deleteById(id);
    }
}
