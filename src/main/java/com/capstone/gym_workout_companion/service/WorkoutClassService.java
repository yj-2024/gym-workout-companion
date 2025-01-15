package com.capstone.gym_workout_companion.service;

import com.capstone.gym_workout_companion.model.WorkoutClass;
import com.capstone.gym_workout_companion.repository.WorkoutClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutClassService {

    @Autowired
    private WorkoutClassRepository workoutClassRepository;

    // Create a new workout class
    public WorkoutClass createWorkoutClass(WorkoutClass workoutClass) {
        return workoutClassRepository.save(workoutClass);
    }
    // Get all workout classes
    public List<WorkoutClass> getAllWorkoutClasses() {
        return workoutClassRepository.findAll();
    }

    // Get workout class by ID
    public WorkoutClass getWorkoutClassById(Long id) {
        return workoutClassRepository.findById(id).orElse(null);
    }

    // Update workout class details
    @Transactional
    public WorkoutClass updateWorkoutClass(Long id, WorkoutClass updatedClass) {
        Optional<WorkoutClass> existingClass = workoutClassRepository.findById(id);
        if (existingClass.isPresent()) {
            WorkoutClass workoutClass = existingClass.get();
            workoutClass.setName(updatedClass.getName());
            workoutClass.setDescription(updatedClass.getDescription());
            workoutClass.setDate(updatedClass.getDate());
            workoutClass.setCapacity(updatedClass.getCapacity());
            return workoutClassRepository.save(workoutClass);
        }
        return null; // If class doesn't exist, return null or throw an exception
    }

    // Delete workout class by ID
    @Transactional
    public boolean deleteWorkoutClass(Long id) {
        if (workoutClassRepository.existsById(id)) {
            workoutClassRepository.deleteById(id);
            return true;
        }
        return false; // If workout class doesn't exist, return false
    }

    // Get all workout classes by date (for listing classes on a specific day)
    // Add custom queries in WorkoutClassRepository interface.
    public List<WorkoutClass> getWorkoutClassesByDate(String date) {
        return workoutClassRepository.findWorkoutClassByDate(LocalDateTime.parse(date));
    }

    // Get available classes (those that are not full)
    public List<WorkoutClass> getAvailableClasses() {
        return workoutClassRepository.findAvailableClasses();
    }

    // Get class count (useful for displaying statistics)
    public long getClassCount() {
        return workoutClassRepository.count();
    }

    // Update class capacity after booking a class
    @Transactional
    public boolean updateClassCapacity(Long classId, int bookedSeats) {
        Optional<WorkoutClass> classOpt = workoutClassRepository.findById(classId);
        if (classOpt.isPresent()) {
            WorkoutClass workoutClass = classOpt.get();
            int newCapacity = workoutClass.getCapacity() - bookedSeats;
            if (newCapacity >= 0) {
                workoutClass.setCapacity(newCapacity);
                workoutClassRepository.save(workoutClass);
                return true;
            }
        }
        return false; // If the class doesn't exist or not enough capacity
    }

    // Add this method to fetch workout classes by date
    public List<WorkoutClass> getClassesByDate(String date) {
        // Use the repository to find classes by date
        return workoutClassRepository.findWorkoutClassByDate(LocalDateTime.parse(date));
    }
}