package com.attendance.student_attendance_system.service;

import com.attendance.student_attendance_system.model.Student;
import com.attendance.student_attendance_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(String id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student getStudentByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    public Student saveStudent(Student student) {
        // Always save as a new student if id is null
        return studentRepository.save(student);
    }

    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }
}
