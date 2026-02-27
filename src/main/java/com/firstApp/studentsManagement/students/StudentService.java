package com.firstApp.studentsManagement.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Find all students
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    // Find student by ID
    public Optional<Student> findStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // Find student by email
    public Optional<Student> findStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    // Find students by last name
    public List<Student> findStudentsByLastName(String lastName) {
        return studentRepository.findByLastName(lastName);
    }

    // Find students older than age
    public List<Student> findStudentsByAgeGreaterThan(Integer age) {
        return studentRepository.findByAgeGreaterThan(age);
    }

    // Find students younger than age
    public List<Student> findStudentsByAgeLessThan(Integer age) {
        return studentRepository.findByAgeLessThan(age);
    }

    // Find students between ages
    public List<Student> findStudentsByAgeBetween(Integer minAge, Integer maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    // Find students by email domain
    public List<Student> findStudentsByEmailDomain(String domain) {
        return studentRepository.findStudentsByEmailDomain("@" + domain);
    }

    // Search by name
    public List<Student> searchByName(String name) {
        return studentRepository.searchByName(name);
    }

    // Add new student
    @Transactional
    public Student addStudent(Student student) {
        // Check if email already exists
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new IllegalStateException("Email already exists");
        }
        return studentRepository.save(student);
    }

    // Update student
    @Transactional
    public Optional<Student> updateStudent(Long id, Student studentDetails) {
        return studentRepository.findById(id)
                .map(existingStudent -> {
                    // Check if email is being changed and already exists
                    if (!existingStudent.getEmail().equals(studentDetails.getEmail()) &&
                            studentRepository.existsByEmail(studentDetails.getEmail())) {
                        throw new IllegalStateException("Email already exists");
                    }

                    existingStudent.setFirstName(studentDetails.getFirstName());
                    existingStudent.setLastName(studentDetails.getLastName());
                    existingStudent.setEmail(studentDetails.getEmail());
                    existingStudent.setAge(studentDetails.getAge());

                    return studentRepository.save(existingStudent);
                });
    }

    // Delete student by ID
    @Transactional
    public boolean deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Delete student by email
    @Transactional
    public boolean deleteStudentByEmail(String email) {
        Optional<Student> student = studentRepository.findByEmail(email);
        if (student.isPresent()) {
            studentRepository.delete(student.get());
            return true;
        }
        return false;
    }

    // Delete all students
    @Transactional
    public void deleteAllStudents() {
        studentRepository.deleteAll();
    }

    // Get student count
    public long getStudentCount() {
        return studentRepository.count();
    }

    // Check if student exists by ID
    public boolean existsById(Long id) {
        return studentRepository.existsById(id);
    }

    // Get average age
    public Double getAverageAge() {
        return studentRepository.getAverageAge();
    }

    // Get oldest student
    public Optional<Student> getOldestStudent() {
        return studentRepository.findOldestStudent();
    }

    // Get youngest student
    public Optional<Student> getYoungestStudent() {
        return studentRepository.findYoungestStudent();
    }

    // Find all ordered by last name
    public List<Student> findAllOrderedByLastName() {
        return studentRepository.findAllByOrderByLastNameAsc();
    }

    // Find all ordered by age descending
    public List<Student> findAllOrderedByAgeDesc() {
        return studentRepository.findAllByOrderByAgeDesc();
    }
}