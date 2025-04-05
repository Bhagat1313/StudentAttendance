package com.attendance.student_attendance_system.controller;

// ** ADD THESE IMPORTS **
import com.attendance.student_attendance_system.dto.DailyReportDto;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
// **********************

import com.attendance.student_attendance_system.model.Attendance;
import com.attendance.student_attendance_system.model.Student;
import com.attendance.student_attendance_system.service.AttendanceService;
import com.attendance.student_attendance_system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private StudentService studentService;

    // showAttendancePage remains the same
    @GetMapping
    public String showAttendancePage(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {

        if (date == null) {
            date = LocalDate.now();
        }

        System.out.println("Attendance Page - Selected date: " + date);
        List<Student> students = studentService.getAllStudents();
        System.out.println("Number of students: " + students.size());
        if (!students.isEmpty()) {
            System.out.println("First student name: " + students.get(0).getName());
        }


        model.addAttribute("date", date);
        model.addAttribute("students", students);
        return "mark-attendance";
    }

    // markAttendance remains the same
    @PostMapping("/mark")
    public String markAttendance(
            @RequestParam String studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam String status) {

        System.out.println("Marking attendance for student ID: " + studentId);
        System.out.println("Date: " + date);
        System.out.println("Status: " + status);

        Attendance attendance = new Attendance();
        attendance.setStudentId(studentId);
        attendance.setDate(date);
        attendance.setTime(LocalTime.now());
        try { // Add try-catch for robustness
            attendance.setStatus(Attendance.AttendanceStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid status value received: " + status);
            // Handle error appropriately - maybe redirect with an error message
            // For now, default to ABSENT or skip saving
            attendance.setStatus(Attendance.AttendanceStatus.ABSENT); // Example fallback
        }


        Attendance saved = attendanceService.markAttendance(attendance);
        System.out.println("Attendance saved with ID: " + (saved != null ? saved.getId() : "null"));

        return "redirect:/attendance?date=" + date;
    }

    // **** UPDATED dailyReport METHOD ****
    @GetMapping("/report/daily")
    public String dailyReport(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {

        if (date == null) {
            date = LocalDate.now();
        }
        System.out.println("Daily Report - Requested date: " + date);

        List<Attendance> attendanceList = attendanceService.getAttendanceByDate(date);
        System.out.println("Found " + attendanceList.size() + " attendance records for date: " + date);

        // Fetch all students and put them in a Map for efficient lookup by ID
        Map<String, Student> studentMap = studentService.getAllStudents().stream()
                .filter(s -> s.getId() != null) // Ensure student has an ID
                .collect(Collectors.toMap(Student::getId, Function.identity()));
        System.out.println("Total students mapped: " + studentMap.size());


        // Create the list of DTOs for the template
        List<DailyReportDto> reportEntries = new ArrayList<>();
        for (Attendance attendance : attendanceList) {
            Student student = null;
            if (attendance.getStudentId() != null) {
                student = studentMap.get(attendance.getStudentId()); // Look up student in the map
            }

            // Create DTO entry
            DailyReportDto dto = new DailyReportDto();
            dto.setDate(attendance.getDate());
            dto.setStatus(attendance.getStatus());
            dto.setTime(attendance.getTime());

            if (student != null) {
                // Populate student details if found
                dto.setStudentDisplayId(student.getStudentId()); // Use the user-facing ID
                dto.setStudentName(student.getName());
                dto.setClassName(student.getClassName());
                dto.setSection(student.getSection());
                System.out.println("Mapped attendance ID " + attendance.getId() + " to student " + student.getName());
            } else {
                // Handle cases where student ID in attendance record is null or doesn't match any student
                dto.setStudentDisplayId("Unknown");
                dto.setStudentName("Unknown Student (" + attendance.getStudentId() + ")"); // Show missing ID
                dto.setClassName("N/A");
                dto.setSection("N/A");
                System.out.println("Could not map attendance ID " + attendance.getId() + " with studentId '" + attendance.getStudentId() + "' to a known student.");
            }
            reportEntries.add(dto);
        }

        // Pass the prepared list of DTOs to the model
        model.addAttribute("reportEntries", reportEntries); // ** PASS reportEntries **
        model.addAttribute("date", date); // Keep the date for display

        return "daily-report";
    }
    // ************************************

    // studentReport remains the same for now, but should ideally also use DTOs
    @GetMapping("/report/student/{id}")
    public String studentReport(@PathVariable String id, Model model) {
        Student student = studentService.getStudentById(id);
        System.out.println("Student Report - Student: " + (student != null ? student.getName() : "null"));

        List<Attendance> attendanceList = attendanceService.getAttendanceByStudentId(id);
        System.out.println("Found " + attendanceList.size() + " attendance records for student ID: " + id);

        model.addAttribute("student", student);
        model.addAttribute("attendanceList", attendanceList); // Still passing raw list here

        return "student-report"; // This template might need updating if you use DTOs here too
    }
}
