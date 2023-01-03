package com.example.datajpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "Enrolment")
@Table(name = "enrolment")
public class Enrolment {

    @EmbeddedId
    private EnrolmentId enrolmentId;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(
            name = "student_id",
            foreignKey = @ForeignKey(name = "enrolment_student_id_fk")
    )
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(
            name = "course_id",
            foreignKey = @ForeignKey(name = "enrolment_course_id_fk")
    )
    private Course course;

    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime createdAt;

    public Enrolment() {
    }

    public Enrolment(Student student, Course course) {
        this.student = student;
        this.course = course;
        enrolmentId = new EnrolmentId();
        enrolmentId.setStudentId(student.getId());
        enrolmentId.setCourseId(course.getId());
        this.createdAt = LocalDateTime.now();
    }

    public Enrolment(Student student, Course course, LocalDateTime createdAt) {
        this.student = student;
        this.course = course;
        enrolmentId = new EnrolmentId();
        enrolmentId.setStudentId(student.getId());
        enrolmentId.setCourseId(course.getId());
        this.createdAt = createdAt;
    }

}
