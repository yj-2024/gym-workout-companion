package com.capstone.gym_workout_companion;

import com.capstone.gym_workout_companion.controller.SignUpController;
import com.capstone.gym_workout_companion.dto.UserDTO;
import com.capstone.gym_workout_companion.model.User;
import com.capstone.gym_workout_companion.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class SignUpControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private SignUpController signUpController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Set up MockMvc for testing the controller
        mockMvc = MockMvcBuilders.standaloneSetup(signUpController).build();
    }

    @Test
    void testHandleSignUp_EmailAlreadyExists() throws Exception {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("johndoe@example.com");
        userDTO.setPassword("password123");
        userDTO.setPhone("1234567890");

        // Mock the behavior of userService to simulate the case where the email already exists
        when(userService.emailExists(userDTO.getEmail())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/signup-process")
                        .param("firstName", userDTO.getFirstName())
                        .param("lastName", userDTO.getLastName())
                        .param("email", userDTO.getEmail())
                        .param("password", userDTO.getPassword())
                        .param("phone", userDTO.getPhone()))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "Email already exists!"));

        verify(userService, times(0)).createUser(any(User.class));
    }
}