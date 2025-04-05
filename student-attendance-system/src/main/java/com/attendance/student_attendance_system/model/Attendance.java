package com.attendance.student_attendance_system.model;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalTime;
@Data
@Document(collection = "attendance")
public class Attendance {
    @Id
    private String id;
    private String studentId;
    private LocalDate date;
    private LocalTime time;
    private AttendanceStatus status;

    public enum AttendanceStatus {
        PRESENT, ABSENT, LATE
    }

    // Default constructor
    public Attendance() {
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }
}
