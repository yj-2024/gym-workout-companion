package com.capstone.gym_workout_companion.repository;

// WorkoutClassRepository.java

import com.capstone.gym_workout_companion.model.WorkoutClass;
import com.capstone.gym_workout_companion.model.WorkoutClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

// Use JpaRepository to interact with the database entities.
// The repository will automatically be wired by Spring Boot when we use @Autowired in our services or controllers.
// Repository interfaces enable Spring Data JPA to automatically handle the persistence layer,
// making it easier for us to interact with our database without needing to manually write SQL queries.

@Repository
public interface WorkoutClassRepository extends JpaRepository<WorkoutClass, Long> {

    // Custom query to find workout classes by name
    List<WorkoutClass> findByName(String name);

    // Custom query to find all classes by a specific date
    List<WorkoutClass> findWorkoutClassByDate(LocalDateTime date);

    // Custom query to find available classes (those with capacity > 0)
    @Query("SELECT wc FROM WorkoutClass wc WHERE wc.capacity > 0")
    List<WorkoutClass> findAvailableClasses();

    // Custom query to find all classes that are full (capacity reached)
    List<WorkoutClass> findByCapacity(int capacity);
}