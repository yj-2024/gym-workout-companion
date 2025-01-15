package com.capstone.gym_workout_companion.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
The UserDTO class is a Data Transfer Object (DTO) used for transferring user data
between layers of the application. It is designed to handle user data,
*/

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    @NotBlank
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]*$", message = "First name must contain only letters")
    private String firstName;

    @NotBlank
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Last name must contain only letters")
    private String lastName;

    @Email
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long, contain at least one uppercase letter, " +
                    "one lowercase letter, one digit, and one special character (e.g., @, $, !, %, *).")
    private String password;

    @Pattern(regexp = "^(\\+?\\d{1,4}[-.\\s]?)?\\(?\\d{1,4}\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}$",
            message = "Please enter a valid phone number. The format should be: (123) 456-7890, 123-456-7890, or +1 123-456-7890.")
    private String phone;
}
