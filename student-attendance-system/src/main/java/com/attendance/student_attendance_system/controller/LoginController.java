package com.attendance.student_attendance_system.controller;

// Remove HttpSession imports if they were added
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    // --- Hardcoded Credentials (INSECURE!) ---
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "password";
    // -----------------------------------------

    @GetMapping("/login")
    public String showLoginPage(Model model, @RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout) {
        if (error != null) {
            model.addAttribute("loginError", "Invalid username or password.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out.");
        }
        // Add a flag to tell the JS if login is needed
        model.addAttribute("needsLogin", true);
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(
            @RequestParam String username,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) {

        System.out.println("Login attempt: username=" + username);

        if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
            System.out.println("Login credentials valid for user: " + username);
            // Redirect to home page, add parameter to signal success to JS
            return "redirect:/?loggedIn=true&user=" + username;
        } else {
            System.out.println("Login credentials invalid for user: " + username);
            redirectAttributes.addAttribute("error", "true");
            return "redirect:/login";
        }
    }

    // Logout doesn't need server interaction beyond redirecting
    // The actual "logout" state is handled purely in the browser's JavaScript
    @GetMapping("/logout-redirect") // Changed path slightly to avoid conflicts if needed
    public String processLogoutRedirect(RedirectAttributes redirectAttributes) {
        System.out.println("Redirecting after logout signal.");
        redirectAttributes.addAttribute("logout", "true");
        return "redirect:/login";
    }
}
