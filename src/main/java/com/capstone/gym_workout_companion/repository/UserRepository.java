package com.capstone.gym_workout_companion.repository;

// UserRepository.java

import com.capstone.gym_workout_companion.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
// Spring Data JPA will automatically provide implementations for CRUD methods
// Use JpaRepository to interact with the database entities.
// Repository interfaces enable Spring Data JPA to automatically handle the persistence layer,
// making it easier for us to interact with our database without needing to manually write SQL queries.
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query to find a user by email
    Optional<User> findByEmail(String email);

    // Custom query to check if a user exists by email (useful for login validation)
    boolean existsByEmail(String email);

    // Custom query to find users by their first name (example)
    List<User> findByFirstName(String firstName);
}