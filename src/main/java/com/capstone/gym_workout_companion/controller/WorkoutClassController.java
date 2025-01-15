package com.capstone.gym_workout_companion.controller;

import com.capstone.gym_workout_companion.dto.UserDTO;
import com.capstone.gym_workout_companion.model.WorkoutClass;
import com.capstone.gym_workout_companion.service.WorkoutClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/workout-classes")
public class WorkoutClassController {

    @Autowired
    private WorkoutClassService workoutClassService;

    // Show the workout-classes page
    @GetMapping()
    public String showWorkoutClassesForm(Model model) {
        return "workout-classes";  // This corresponds to workout-classes.html in the templates folder
    }

    @GetMapping("/confirmation")
    public String showWorkoutClassesConfirmation(Model model) {
        return "workout-classes-confirmation-page";  // This corresponds to workout-classes.html in the templates folder
    }

    // Create a new workout class
    @PostMapping("/create-class")
    public ResponseEntity<WorkoutClass> createWorkoutClass(@RequestBody WorkoutClass workoutClass) {
        WorkoutClass createdClass = workoutClassService.createWorkoutClass(workoutClass);
        return ResponseEntity.status(201).body(createdClass);
    }

    // Get all workout classes
    @GetMapping("/classes")
    public List<WorkoutClass> getAllWorkoutClasses() {
        return workoutClassService.getAllWorkoutClasses();
    }

    // Get workout class by ID
    @GetMapping("/class/{id}")
    public ResponseEntity<WorkoutClass> getWorkoutClassById(@PathVariable Long id) {
        WorkoutClass workoutClass = workoutClassService.getWorkoutClassById(id);
        return workoutClass != null ? ResponseEntity.ok(workoutClass) : ResponseEntity.status(404).build();
    }

    // Update workout class by ID
    @PutMapping("/class/{id}")
    public ResponseEntity<WorkoutClass> updateWorkoutClass(@PathVariable Long id, @RequestBody WorkoutClass updatedClass) {
        WorkoutClass updatedWorkoutClass = workoutClassService.updateWorkoutClass(id, updatedClass);
        return updatedWorkoutClass != null ?
                ResponseEntity.ok(updatedWorkoutClass) :
                ResponseEntity.status(404).build();
    }

    // Delete workout class by ID
    @DeleteMapping("/class/{id}")
    public ResponseEntity<Void> deleteWorkoutClass(@PathVariable Long id) {
        boolean deleted = workoutClassService.deleteWorkoutClass(id);
        return deleted ? ResponseEntity.status(204).build() : ResponseEntity.status(404).build();
    }

    /*
    Get available workout classes (those with capacity > 0)
    @GetMapping("/available-classes") is an annotation in Spring that maps HTTP GET requests to a method in the controller.
    The URL /available-classes specifies the endpoint for which the method will be triggered.
    When a client makes a GET request to /available-classes, this method is invoked to fetch available workout classes
    (likely with capacity > 0) from the service and return them in the response.
    */
    @GetMapping("/available-classes")
    public List<WorkoutClass> getAvailableClasses() {
        return workoutClassService.getAvailableClasses();
    }

    // Get workout classes by date (e.g., for scheduling purposes)
    @GetMapping("/classes-by-date/{date}")
    public List<WorkoutClass> getClassesByDate(@PathVariable String date) {
        return workoutClassService.getClassesByDate(date);
    }

    /*
     @GetMapping("/classes-by-date/{date}")
    public ResponseEntity<List<WorkoutClass>> getClassesByDate(@PathVariable String date) {
        List<WorkoutClass> workoutClasses = workoutClassService.getClassesByDate(date);
        if (workoutClasses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(workoutClasses);
    }
     */
}