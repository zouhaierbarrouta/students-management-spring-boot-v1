package com.firstApp.studentsManagement.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/students")
public class studentsController {

    private final StudentService studentService;

    @Autowired
    public studentsController(StudentService studentService) {
        this.studentService = studentService;
    }

    // GET all students
    @GetMapping
    public ResponseEntity<List<Student>> findAllStudents() {
        List<Student> students = studentService.findAllStudents();
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // GET student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> findStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.findStudentById(id);
        return student.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET student by email
    @GetMapping("/email/{email}")
    public ResponseEntity<Student> findStudentByEmail(@PathVariable String email) {
        Optional<Student> student = studentService.findStudentByEmail(email);
        return student.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET students by last name
    @GetMapping("/lastname/{lastName}")
    public ResponseEntity<List<Student>> findStudentsByLastName(@PathVariable String lastName) {
        List<Student> students = studentService.findStudentsByLastName(lastName);
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // GET students older than age
    @GetMapping("/older-than/{age}")
    public ResponseEntity<List<Student>> findStudentsByAgeGreaterThan(@PathVariable Integer age) {
        List<Student> students = studentService.findStudentsByAgeGreaterThan(age);
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // GET students younger than age
    @GetMapping("/younger-than/{age}")
    public ResponseEntity<List<Student>> findStudentsByAgeLessThan(@PathVariable Integer age) {
        List<Student> students = studentService.findStudentsByAgeLessThan(age);
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // GET students between ages
    @GetMapping("/between-ages")
    public ResponseEntity<List<Student>> findStudentsByAgeBetween(
            @RequestParam Integer min,
            @RequestParam Integer max) {
        List<Student> students = studentService.findStudentsByAgeBetween(min, max);
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // GET students by email domain
    @GetMapping("/domain/{domain}")
    public ResponseEntity<List<Student>> findStudentsByEmailDomain(@PathVariable String domain) {
        List<Student> students = studentService.findStudentsByEmailDomain(domain);
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // GET search students by name
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudents(@RequestParam String name) {
        List<Student> students = studentService.searchByName(name);
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // POST create new student
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        try {
            Student newStudent = studentService.addStudent(student);
            return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            // Email already exists
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // PUT update student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        try {
            Optional<Student> updatedStudent = studentService.updateStudent(id, student);
            return updatedStudent.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (IllegalStateException e) {
            // Email already exists
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        boolean deleted = studentService.deleteStudent(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE student by email
    @DeleteMapping("/email/{email}")
    public ResponseEntity<Void> deleteStudentByEmail(@PathVariable String email) {
        boolean deleted = studentService.deleteStudentByEmail(email);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE all students
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllStudents() {
        studentService.deleteAllStudents();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // GET student count
    @GetMapping("/count")
    public ResponseEntity<Long> getStudentCount() {
        long count = studentService.getStudentCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // GET average age
    @GetMapping("/average-age")
    public ResponseEntity<Double> getAverageAge() {
        Double averageAge = studentService.getAverageAge();
        return new ResponseEntity<>(averageAge, HttpStatus.OK);
    }

    // GET oldest student
    @GetMapping("/oldest")
    public ResponseEntity<Student> getOldestStudent() {
        Optional<Student> student = studentService.getOldestStudent();
        return student.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET youngest student
    @GetMapping("/youngest")
    public ResponseEntity<Student> getYoungestStudent() {
        Optional<Student> student = studentService.getYoungestStudent();
        return student.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET students ordered by last name
    @GetMapping("/ordered-by-lastname")
    public ResponseEntity<List<Student>> findAllOrderedByLastName() {
        List<Student> students = studentService.findAllOrderedByLastName();
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // GET students ordered by age descending
    @GetMapping("/ordered-by-age-desc")
    public ResponseEntity<List<Student>> findAllOrderedByAgeDesc() {
        List<Student> students = studentService.findAllOrderedByAgeDesc();
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // HEAD check if student exists
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> existsById(@PathVariable Long id) {
        boolean exists = studentService.existsById(id);
        if (exists) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}