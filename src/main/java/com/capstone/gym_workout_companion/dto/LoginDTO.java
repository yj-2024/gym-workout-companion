package com.capstone.gym_workout_companion.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
The LoginDTO class is a Data Transfer Object (DTO) used for handling login requests in the application.
The LoginDTO captures all necessary fields (login credentials like email and password),
and validate them on the backend. Validation annotations are used to ensure the data is in the correct format.
*/

@Getter
@Setter
@NoArgsConstructor
public class LoginDTO {

    // @Email: Ensures that the email field contains a valid email address format.
    @Email
    private String email;

    // @NotBlank: Ensures that the password field is not empty or null.
    @NotBlank
    private String password;

    /*
    The Remember Me checkbox field is a boolean that represents whether the user has selected
    the "Remember Me" option on the login form.
    If true, the system can issue a persistent cookie to keep the user logged in.
    */
    //private boolean rememberMe;

    /*
    The captchaResponse field would store the CAPTCHA response entered by the user.
    On the backend, we need to validate this response with the CAPTCHA service we're using (e.g., Google reCAPTCHA).
    */
    //private String captchaResponse;
}