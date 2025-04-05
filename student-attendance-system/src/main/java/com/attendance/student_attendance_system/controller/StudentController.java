package com.attendance.student_attendance_system.controller;

import com.attendance.student_attendance_system.model.Student;
import com.attendance.student_attendance_system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils; // Import StringUtils
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public String listStudents(Model model) {
        System.out.println("Listing all students: " + studentService.getAllStudents().size() + " students found");
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("student", new Student()); // Empty student for the form
        return "students";
    }

    @PostMapping
    public String saveStudent(@ModelAttribute Student student) {
        System.out.println("Received student: Name=" + student.getName() + ", ID=" + student.getId());

        // **** ADD THIS CHECK ****
        // If the ID is present but empty (from the hidden form field on add), set it to null
        // so that MongoDB generates a new ObjectId.
        if (student.getId() != null && student.getId().isEmpty()) {
            System.out.println("Submitted ID was empty, setting to null for new student insertion.");
            student.setId(null);
        }
        // ***********************

        System.out.println("Saving student: Name=" + student.getName() + ", ID to save=" + student.getId());
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable String id, Model model) {
        System.out.println("Editing student with ID: " + id);
        Student studentToEdit = studentService.getStudentById(id);
        System.out.println("Found student: " + (studentToEdit != null ? studentToEdit.getName() : "null"));

        if (studentToEdit == null) {
            // Optional: Handle case where student to edit is not found
            System.out.println("Student with ID " + id + " not found for editing.");
            // Redirect or show an error
            return "redirect:/students?error=StudentNotFound";
        }

        model.addAttribute("student", studentToEdit); // Use the found student for the form
        model.addAttribute("students", studentService.getAllStudents()); // Still need the list
        return "students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable String id) {
        System.out.println("Deleting student with ID: " + id);
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}
