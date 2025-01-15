package com.capstone.gym_workout_companion.controller;

import com.capstone.gym_workout_companion.model.User;
import com.capstone.gym_workout_companion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    // Show the user's profile page
    @GetMapping
    public String showProfile(Model model) {
        // Get the logged-in user's email from SecurityContext
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();  // The email is used as the username in Spring Security

        // Fetch the user's details from the database using the email
        User user = userService.getUserByEmail(email);

        // If the user exists, display their details on the profile page
        if (user != null) {
            model.addAttribute("user", user);
        }

        return "profile";  // This corresponds to profile.html in the templates folder
    }

    @GetMapping("/confirmation")
    public String showConfirmation(Model model) {
        return "profile-confirmation-page";  // This corresponds to profile-confirmation-page.html in the templates folder
    }

    // Update user's profile information (e.g., name, email, phone)
    @PostMapping("/update")
    public String updateProfile(@RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String email,
                                @RequestParam String phone,
                                Model model) {
        // Get the logged-in user's email from SecurityContext
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentEmail = userDetails.getUsername();

        // Fetch the user's details from the database using the email
        User user = userService.getUserByEmail(currentEmail);

        // If user exists, update their profile
        if (user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPhone(phone);

            // Save the updated user details
            userService.updateUser(user.getId(), user);

            // Add success message to the model
            model.addAttribute("success", "Profile updated successfully!");
        } else {
            // If user does not exist, show an error message
            model.addAttribute("error", "User not found.");
        }

        return "profile";  // Redirect back to the profile page
    }
}