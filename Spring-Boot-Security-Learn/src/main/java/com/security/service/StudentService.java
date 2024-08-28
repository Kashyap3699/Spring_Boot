package com.security.service;

import java.util.ArrayList;
import java.util.List;

import com.security.model.Student;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    List<Student> list = new ArrayList<>();

    public StudentService() {

        list.add(new Student(1,"Kashyap",60));
        list.add(new Student(2,"Karan",70));

    }

    // get all users
    public List<Student> getAllStudent() {
        return list;
    }

    // get single user
    public Student getStudents(String name) {
        return this.list.stream().filter(student -> student.getName().equals(name)).findAny().orElse(null);
    }

    // add new user
    public Student addStudents(Student student) {
        list.add(student);
        return student;

    }
}
