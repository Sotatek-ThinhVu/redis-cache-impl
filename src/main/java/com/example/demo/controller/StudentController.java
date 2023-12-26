package com.example.demo.controller;

import com.example.demo.model.MessageResponse;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("")
    public ResponseEntity<?> addStudent(@RequestBody Student student){
        studentService.save(student);
        return new ResponseEntity<>(new MessageResponse("successfully"), HttpStatus.CREATED) ;
    }
    @GetMapping("/list")
    public ResponseEntity<?> getAllStudents() {
        return new ResponseEntity<>(studentService.findAll(),HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getStudentById(@RequestParam("id") String id) {
        Student student = studentService.findById(id);
        if (student==null){
            return new ResponseEntity<>(new MessageResponse("Not found"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(student,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public void updateStudent(@PathVariable String id, @RequestBody Student student) {
        Student existingStudent = studentService.findById(id);
        if (existingStudent != null) {
            student.setId(id);
            studentService.update(student);
        }
    }
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable String id) {
        studentService.delete(id);
    }

}
