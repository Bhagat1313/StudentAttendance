package com.attendance.student_attendance_system.dto; // Make sure package matches your structure

import com.attendance.student_attendance_system.model.Attendance; // Import if needed
import java.time.LocalDate;
import java.time.LocalTime;

// You can add Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor)
// or write the getters/setters manually as shown below.

public class DailyReportDto {
    private String studentDisplayId; // The ID shown to the user (e.g., "1", "2")
    private String studentName;
    private String className;
    private String section;
    private Attendance.AttendanceStatus status; // Use the enum from Attendance
    private LocalTime time;
    private LocalDate date;

    // --- Manual Getters and Setters (if not using Lombok) ---

    public DailyReportDto() { // Default constructor
    }

    // Constructor (optional, but can be useful)
    public DailyReportDto(String studentDisplayId, String studentName, String className, String section, Attendance.AttendanceStatus status, LocalTime time, LocalDate date) {
        this.studentDisplayId = studentDisplayId;
        this.studentName = studentName;
        this.className = className;
        this.section = section;
        this.status = status;
        this.time = time;
        this.date = date;
    }

    // Getters
    public String getStudentDisplayId() { return studentDisplayId; }
    public String getStudentName() { return studentName; }
    public String getClassName() { return className; }
    public String getSection() { return section; }
    public Attendance.AttendanceStatus getStatus() { return status; }
    public LocalTime getTime() { return time; }
    public LocalDate getDate() { return date; }

    // Setters
    public void setStudentDisplayId(String studentDisplayId) { this.studentDisplayId = studentDisplayId; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public void setClassName(String className) { this.className = className; }
    public void setSection(String section) { this.section = section; }
    public void setStatus(Attendance.AttendanceStatus status) { this.status = status; }
    public void setTime(LocalTime time) { this.time = time; }
    public void setDate(LocalDate date) { this.date = date; }
}
