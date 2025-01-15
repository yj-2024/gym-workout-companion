package com.capstone.gym_workout_companion.repository;

import com.capstone.gym_workout_companion.model.Role;
import com.capstone.gym_workout_companion.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
