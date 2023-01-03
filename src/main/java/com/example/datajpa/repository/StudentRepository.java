package com.example.datajpa.repository;

import com.example.datajpa.entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findStudentByEmail(String email);

    List<Student> findByFirstNameAndAgeGreaterThanEqual(String firstName, Integer age);

    @Query("SELECT s FROM Student s WHERE s.age >= ?1")
    List<Student> findStudentsByFirstNameAndAge(Integer age);

    @Query(value = "SELECT * FROM students s WHERE s.age >= ?1", nativeQuery = true)
    List<Student> findStudentsByFirstNameAndAgeNative(Integer age);

    @Transactional
    @Modifying
    @Query("DELETE FROM Student s WHERE s.id = :id")
    int deleteStudentById(@Param("id") Long id);

}
