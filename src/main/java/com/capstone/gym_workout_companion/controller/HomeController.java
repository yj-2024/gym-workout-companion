package com.capstone.gym_workout_companion.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/home")
    public String homePage() {
        return "index";
    }

    // Users who are already logged in are redirected to the home page
    // (or profile page) if they try to access the login page.
    /*@GetMapping("/login")
    public String login(HttpSession session) {
        if (session.getAttribute("user") != null) {  // Assuming 'user' is stored in the session
            return "redirect:/home"; // Redirect authenticated users to home page
        }
        return "login"; // Otherwise, show login page
    }*/

}