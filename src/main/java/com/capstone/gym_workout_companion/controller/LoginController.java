package com.capstone.gym_workout_companion.controller;

import com.capstone.gym_workout_companion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    // Display the login page
   @GetMapping("/login")
    public String showLoginPage() {
        return "login";  // Return the login.html page. This corresponds to login.html in the templates folder
    }

    // Logout logic, typically handled by Spring Security
    @GetMapping("/logout")
    public String logout() {
        // Spring Security automatically handles the logout
        return "redirect:/";  // Redirect to the home page after logging out
    }
}