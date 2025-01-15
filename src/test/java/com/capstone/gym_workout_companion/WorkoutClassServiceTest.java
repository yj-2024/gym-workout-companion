package com.capstone.gym_workout_companion;

import com.capstone.gym_workout_companion.model.WorkoutClass;
import com.capstone.gym_workout_companion.repository.WorkoutClassRepository;
import com.capstone.gym_workout_companion.service.WorkoutClassService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WorkoutClassServiceTest {

    @Mock
    private WorkoutClassRepository workoutClassRepository;

    @InjectMocks
    private WorkoutClassService workoutClassService;

    private WorkoutClass workoutClass;

    @BeforeEach
    void setUp() {
        // Set up the test data
        workoutClass = new WorkoutClass();
        workoutClass.setId(1L);
        workoutClass.setName("Yoga Class");
        workoutClass.setDescription("A relaxing yoga class");
        workoutClass.setDate(LocalDateTime.of(2025, 1, 15, 10, 0));
        workoutClass.setCapacity(20);
    }

    @Test
    void testCreateWorkoutClass() {
        // Arrange
        when(workoutClassRepository.save(any(WorkoutClass.class))).thenReturn(workoutClass);

        // Act
        WorkoutClass createdClass = workoutClassService.createWorkoutClass(workoutClass);

        // Assert
        assertNotNull(createdClass);
        assertEquals("Yoga Class", createdClass.getName());
        assertEquals(20, createdClass.getCapacity());
        verify(workoutClassRepository, times(1)).save(workoutClass);
    }

    @Test
    void testGetAllWorkoutClasses() {
        // Arrange
        when(workoutClassRepository.findAll()).thenReturn(List.of(workoutClass));

        // Act
        var workoutClasses = workoutClassService.getAllWorkoutClasses();

        // Assert
        assertNotNull(workoutClasses);
        assertEquals(1, workoutClasses.size());
        assertEquals("Yoga Class", workoutClasses.get(0).getName());
        verify(workoutClassRepository, times(1)).findAll();
    }

    @Test
    void testGetWorkoutClassById() {
        // Arrange
        when(workoutClassRepository.findById(1L)).thenReturn(Optional.of(workoutClass));

        // Act
        WorkoutClass foundClass = workoutClassService.getWorkoutClassById(1L);

        // Assert
        assertNotNull(foundClass);
        assertEquals("Yoga Class", foundClass.getName());
        verify(workoutClassRepository, times(1)).findById(1L);
    }

    @Test
    void testGetWorkoutClassById_NotFound() {
        // Arrange
        when(workoutClassRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        WorkoutClass foundClass = workoutClassService.getWorkoutClassById(1L);

        // Assert
        assertNull(foundClass);
        verify(workoutClassRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteWorkoutClass() {
        // Arrange
        when(workoutClassRepository.existsById(1L)).thenReturn(true);

        // Act
        boolean result = workoutClassService.deleteWorkoutClass(1L);

        // Assert
        assertTrue(result);
        verify(workoutClassRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteWorkoutClass_NotFound() {
        // Arrange
        when(workoutClassRepository.existsById(1L)).thenReturn(false);

        // Act
        boolean result = workoutClassService.deleteWorkoutClass(1L);

        // Assert
        assertFalse(result);
        verify(workoutClassRepository, times(1)).existsById(1L);
    }

    @Test
    void testGetWorkoutClassesByDate() {
        // Arrange
        String date = "2025-01-15T10:00:00";
        when(workoutClassRepository.findWorkoutClassByDate(LocalDateTime.parse(date)))
                .thenReturn(List.of(workoutClass));

        // Act
        var workoutClasses = workoutClassService.getWorkoutClassesByDate(date);

        // Assert
        assertNotNull(workoutClasses);
        assertEquals(1, workoutClasses.size());
        assertEquals("Yoga Class", workoutClasses.get(0).getName());
        verify(workoutClassRepository, times(1)).findWorkoutClassByDate(LocalDateTime.parse(date));
    }

    @Test
    void testGetAvailableClasses() {
        // Arrange
        when(workoutClassRepository.findAvailableClasses()).thenReturn(List.of(workoutClass));

        // Act
        var availableClasses = workoutClassService.getAvailableClasses();

        // Assert
        assertNotNull(availableClasses);
        assertEquals(1, availableClasses.size());
        assertEquals("Yoga Class", availableClasses.get(0).getName());
        verify(workoutClassRepository, times(1)).findAvailableClasses();
    }

    @Test
    void testUpdateClassCapacity() {
        // Arrange
        when(workoutClassRepository.findById(1L)).thenReturn(Optional.of(workoutClass));
        when(workoutClassRepository.save(any(WorkoutClass.class))).thenReturn(workoutClass);

        // Act
        boolean result = workoutClassService.updateClassCapacity(1L, 5);

        // Assert
        assertTrue(result);
        assertEquals(15, workoutClass.getCapacity()); // 20 - 5 = 15
        verify(workoutClassRepository, times(1)).findById(1L);
        verify(workoutClassRepository, times(1)).save(workoutClass);
    }

    @Test
    void testUpdateClassCapacity_InsufficientSeats() {
        // Arrange
        workoutClass.setCapacity(2); // Set the class capacity to 2
        when(workoutClassRepository.findById(1L)).thenReturn(Optional.of(workoutClass));

        // Act
        boolean result = workoutClassService.updateClassCapacity(1L, 5); // Attempt to book 5 seats

        // Assert
        assertFalse(result); // Not enough capacity
        verify(workoutClassRepository, times(1)).findById(1L);
        verify(workoutClassRepository, times(0)).save(any(WorkoutClass.class));
    }
}