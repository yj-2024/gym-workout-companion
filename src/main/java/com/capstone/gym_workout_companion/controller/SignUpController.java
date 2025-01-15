package com.capstone.gym_workout_companion.controller;

import com.capstone.gym_workout_companion.dto.UserDTO;
import com.capstone.gym_workout_companion.model.User;
import com.capstone.gym_workout_companion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {

    @Autowired
    private UserService userService;

    // Show the signup page
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new UserDTO());  // Create an empty user object for binding form data
        return "signup";  // This corresponds to signup.html in the templates folder
    }

    // Handle the signup form submission
    @PostMapping("/signup-process")
    public String handleSignUp(UserDTO userDTO, Model model) { // class and object

        // Check if the email already exists in the database
        if (userService.emailExists(userDTO.getEmail())) {
            model.addAttribute("error", "Email already exists!");
            return "signup";  // If email exists, return to signup page with error message
        }

        // Create new user object
        User newUser = new User();
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(userDTO.getPassword());  // Make sure password is encoded in UserService before saving
        newUser.setPhone(userDTO.getPhone());

        // Save the new user in the database
        userService.createUser(newUser);

        // Redirect to the login page or home page after successful registration
        return "redirect:/login";  // This redirects to login.html for login after signup
    }
}