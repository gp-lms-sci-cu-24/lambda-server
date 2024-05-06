package com.cu.sci.lambdaserver.student.service;

import com.cu.sci.lambdaserver.contactinfo.service.ContactInfoService;
import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.department.DepartmentRepository;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.StudentRepository;
import com.cu.sci.lambdaserver.student.dto.*;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService implements IStudentService {
    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final IMapper<Student, CreateStudentRequestDto> createStudentRequestDtoMapper;
    private final IMapper<Student, UpdateStudentDto> updateStudentDtoIMapper;
    private final IMapper<Student, StudentDto> studentDtoiMapper;
    private final PasswordEncoder passwordEncoder;


    public void initStudent(Student student, Department department){
        student.setDepartment(department );
        student.setUsername(student.getCode() );
        student.setPassword(passwordEncoder.encode(student.getPassword()));
    }
    @Override
    public StudentDto creatStudent(CreateStudentRequestDto studentDto) throws ResponseStatusException {
        // check department
        Optional<Department> department = departmentRepository
                .findDepartmentByCodeIgnoreCase(studentDto.getDepartmentCode());
        if (department.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "department not found with this code");
        }

        Optional<Student> foundedStudentByCode = studentRepository.findByCode(studentDto.getCode());
        if (foundedStudentByCode.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Code is already exist.");
        }


        Student student = createStudentRequestDtoMapper.mapFrom(studentDto);

        student.setDepartment(department.get());
        student.setUsername(studentDto.getCode());
        student.setPassword(passwordEncoder.encode(studentDto.getPassword()));

        log.info("Student: {}", student);
        Student saveStudent = studentRepository.save(student);


        //save student
        return studentDtoiMapper.mapTo(saveStudent);
    }

    @Override
    public Page<StudentDto> getAllStudents(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Student> students = studentRepository.findAll(pageable);
        //check if list empty
        if (students.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, " did not find any students");
        }
        return students.map(studentDtoiMapper::mapTo);
    }

    @Override
    public StudentDto getStudent(String code) {
        Optional<Student> student = studentRepository.findByCode(code);
        if (student.isEmpty()) {
            // this throws a null error when students table is empty
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " student not found with this code ");
        }
        return studentDtoiMapper.mapTo(student.get());

    }

    @Override
    public StudentDto updateStudent(String code, UpdateStudentDto studentDetails) {
        //check if user exist
        Optional<Student> student = studentRepository.findByCode(code);

        if (student.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " student not found with this code ");
        }

        //update student
        student.map(foundStudent -> {
            Optional.ofNullable(studentDetails.getGpa()).ifPresent(foundStudent::setGpa);
            Optional.ofNullable(studentDetails.getCreditHours()).ifPresent(foundStudent::setCreditHours);
            Optional.ofNullable(studentDetails.getLevel()).ifPresent(foundStudent::setLevel);
            return studentRepository.save(foundStudent);
        });
        return studentDtoiMapper.mapTo(student.get());
    }
    @Override
    public Collection<StudentDto> createStudents(CreateStudentsDto createStudentsDto) {
        // insert without bulking for 10 k records: 1 minute and 39 seconds
        // insert with bulking for 10 k records with batch size = 100: 18 seconds
        // !!!!!!!!!!!!

        // do not allow updates in departments
        List<CreateStudentRequestDto> students = createStudentsDto.getStudents();
        List<Student> studentList = new ArrayList<>(students.size() );
        Map<String, Department> departmentMap = new HashMap<>();

        for (int i = 0; i < students.size(); i++) {
            Student student = createStudentRequestDtoMapper.mapFrom(students.get(i) );
            Department department;
            if(departmentMap.containsKey(students.get(i).getDepartmentCode() ) ){
                department = departmentMap.get(students.get(i).getDepartmentCode() );
            }
            else{
                System.out.println("calling db to get the department");
                department = departmentRepository
                        .findDepartmentByCodeIgnoreCase(students.get(i).getDepartmentCode())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "department not found with this code") );
                departmentMap.put(students.get(i).getDepartmentCode(), department);
            }
            initStudent(student,department);
            studentList.add(student);
        }
        return studentRepository.saveAll(studentList)
                .stream().map(studentDtoiMapper::mapTo).collect(Collectors.toList());
    }

    @Override
    public Collection<StudentDto> updateStudents(UpdateStudentsDto updateStudentsDto) {
        Map<String, UpdateStudentDto> studentMap = updateStudentsDto.getStudents().stream()
                .collect(Collectors.toMap(UpdateStudentDto::getCode, student -> student) );
        Collection<Student> dbStudents = studentRepository
                .getByCodeIn(
                        updateStudentsDto.getStudents().stream().map(UpdateStudentDto::getCode).collect(Collectors.toList() )
                );
        for(Student student: dbStudents){
            studentMap.get(student.getCode() ).setCode(null);
            updateStudentDtoIMapper.update(studentMap.get(student.getCode() ), student);
        }
        return studentRepository.saveAll(dbStudents)
                .stream().map(studentDtoiMapper::mapTo).collect(Collectors.toList());
    }

    @Override
    public void deleteStudent(String code) {
        //check if student exist
        Optional<Student> student = studentRepository.findByCode(code);
        if (student.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " student not found with this code ");
        }
        //delete student
        studentRepository.delete(student.get());
    }
//    public void bulkTest(){
//        List<Student> studentList = new ArrayList<>();
//        Department department = departmentRepository
//                .findDepartmentByCodeIgnoreCase("GEN")
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "department not found with this code") );
//        for (int i = 0; i < 10000; i++) {
//            Student student = new Student();
//            student.setFirstName("FirstName" + i);
//            student.setFatherName("FatherName" + i);
//            student.setGrandfatherName("GrandfatherName" + i);
//            student.setLastname("Lastname" + i);
//            student.setCode("tode" + i);
//            student.setUsername(student.getCode() );
//            student.setPassword("student");
//            student.setCreditHours(i * 3); // Just an example calculation for credit hours
//            student.setAddress("Address" + i);
//            student.setJoiningYear("2020"); // Example joining year
//            student.setDepartment(department);
//
//            studentList.add(student);
//        }
//    }
}
