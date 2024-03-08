package com.cu.sci.lambdaserver.StudentPackage;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/students")
public class StudentController {

    @GetMapping
    public  String getAllStudents(){
        return "getAllStudents" ;
    }

    @GetMapping("/{id}")
    public String getStudentById (@PathVariable long id ){
        return "This Id" + id ;
    }


}
