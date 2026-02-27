package com.firstApp.studentsManagement.students;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Find student by email
    Optional<Student> findByEmail(String email);

    // Check if email exists
    boolean existsByEmail(String email);

    // Find students by last name
    List<Student> findByLastName(String lastName);

    // Find students by first name (case insensitive)
    List<Student> findByFirstNameIgnoreCase(String firstName);

    // Find students older than a certain age
    List<Student> findByAgeGreaterThan(Integer age);

    // Find students younger than a certain age
    List<Student> findByAgeLessThan(Integer age);

    // Find students between ages
    List<Student> findByAgeBetween(Integer minAge, Integer maxAge);

    // Custom query to find students by email domain
    @Query("SELECT s FROM Student s WHERE s.email LIKE %:domain")
    List<Student> findStudentsByEmailDomain(@Param("domain") String domain);

    // Custom query to search students by name (first or last)
    @Query("SELECT s FROM Student s WHERE " +
            "LOWER(s.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Student> searchByName(@Param("searchTerm") String searchTerm);

    // Find students whose first name starts with
    List<Student> findByFirstNameStartingWith(String prefix);

    // Count students by last name
    Long countByLastName(String lastName);

    // Delete student by email
    void deleteByEmail(String email);

    // Find oldest student
    @Query("SELECT s FROM Student s WHERE s.age = (SELECT MAX(age) FROM Student)")
    Optional<Student> findOldestStudent();

    // Find youngest student
    @Query("SELECT s FROM Student s WHERE s.age = (SELECT MIN(age) FROM Student)")
    Optional<Student> findYoungestStudent();

    // Get average age
    @Query("SELECT AVG(s.age) FROM Student s")
    Double getAverageAge();

    // Find all ordered by last name
    List<Student> findAllByOrderByLastNameAsc();

    // Find all ordered by age descending
    List<Student> findAllByOrderByAgeDesc();
}