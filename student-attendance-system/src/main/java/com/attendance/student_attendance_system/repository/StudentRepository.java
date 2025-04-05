package com.attendance.student_attendance_system.repository;

import com.attendance.student_attendance_system.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String> {
    Student findByStudentId(String studentId);
}

