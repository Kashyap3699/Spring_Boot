package com.security.controller;

import java.util.List;

import com.security.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    //------------get All Users-------------
    @GetMapping("/")
    public List<Student> getAllStudents() {
        return studentService.getAllStudent();
    }

    //-----------get Single user------------
    @GetMapping("/{name}")
    public Student getStudent(@PathVariable("name") String name) {
        return studentService.getStudents(name);


    }

    @PostMapping("/useradd")
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudents(student);

    }


}
