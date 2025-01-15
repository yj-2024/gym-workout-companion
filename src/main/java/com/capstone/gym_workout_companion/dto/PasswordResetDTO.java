package com.capstone.gym_workout_companion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
The PasswordResetDto class is a Data Transfer Object (DTO) that will handle
the new password and confirmation password.
This DTO will allow the user to submit both the new password and the confirmation password
to ensure that both values match before the password is updated.
*/

@Getter
@Setter
@NoArgsConstructor
public class PasswordResetDTO {

    @NotBlank(message = "New password is required.")
    @Size(min = 8, message = "New password must be at least 8 characters long.")
    private String newPassword;

    @NotBlank(message = "Confirmation password is required.")
    @Size(min = 8, message = "Confirmation password must be at least 8 characters long.")
    private String confirmPassword;

    // Custom validation logic to ensure both passwords match
    public boolean isValidPasswordMatch() {
        return newPassword != null && newPassword.equals(confirmPassword);
    }
}