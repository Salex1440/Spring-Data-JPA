package com.example.datajpa.repository;

import com.example.datajpa.entity.Book;
import com.example.datajpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.student = ?1")
    List<Book> findBooksByStudent(Student student);

    @Query(value = "SELECT b FROM Book b WHERE b.student.id = ?1")
    List<Book> findBooksByStudentId(Long studentId);


}
