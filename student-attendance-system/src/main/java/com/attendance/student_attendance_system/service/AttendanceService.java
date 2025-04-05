package com.attendance.student_attendance_system.service;

import com.attendance.student_attendance_system.model.Attendance;
import com.attendance.student_attendance_system.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Attendance> getAllAttendance() {
        List<Attendance> allAttendance = attendanceRepository.findAll();
        System.out.println("Total attendance records in system: " + allAttendance.size());
        return allAttendance;
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        System.out.println("Finding attendance for date: " + date);
        List<Attendance> results = attendanceRepository.findByDate(date);
        System.out.println("Found " + results.size() + " attendance records for date: " + date);

        // Debug the actual data found
        if (!results.isEmpty()) {
            System.out.println("First record - Student ID: " + results.get(0).getStudentId() +
                    ", Status: " + results.get(0).getStatus());
        }

        return results;
    }

    public List<Attendance> getAttendanceByStudentId(String studentId) {
        System.out.println("Finding attendance for student ID: " + studentId);
        List<Attendance> results = attendanceRepository.findByStudentId(studentId);
        System.out.println("Found " + results.size() + " attendance records for student ID: " + studentId);
        return results;
    }

    public Attendance markAttendance(Attendance attendance) {
        System.out.println("Marking attendance - Student ID: " + attendance.getStudentId() +
                ", Date: " + attendance.getDate() +
                ", Status: " + attendance.getStatus());

        // Check if attendance already marked for student on this date
        List<Attendance> existing = attendanceRepository
                .findByStudentIdAndDate(attendance.getStudentId(), attendance.getDate());

        System.out.println("Found " + existing.size() + " existing records for this student and date");

        if (!existing.isEmpty()) {
            // Update existing record
            Attendance existingAttendance = existing.get(0);
            System.out.println("Updating existing attendance record ID: " + existingAttendance.getId());
            existingAttendance.setStatus(attendance.getStatus());
            existingAttendance.setTime(attendance.getTime());
            return attendanceRepository.save(existingAttendance);
        }

        // Create new record
        System.out.println("Creating new attendance record");
        Attendance saved = attendanceRepository.save(attendance);
        System.out.println("New attendance record created with ID: " + saved.getId());
        return saved;
    }

    public void deleteAttendance(String id) {
        System.out.println("Deleting attendance record ID: " + id);
        attendanceRepository.deleteById(id);
    }
}
