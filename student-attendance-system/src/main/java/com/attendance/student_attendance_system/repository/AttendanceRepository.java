package com.attendance.student_attendance_system.repository;

import com.attendance.student_attendance_system.model.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends MongoRepository<Attendance, String> {
    List<Attendance> findByDate(LocalDate date);
    List<Attendance> findByStudentId(String studentId);
    List<Attendance> findByStudentIdAndDate(String studentId, LocalDate date);
}

