package com.capstone.gym_workout_companion.repository;

// EquipmentLogRepository.java

import com.capstone.gym_workout_companion.model.EquipmentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

// Use JpaRepository to interact with the database entities.
// The repository will automatically be wired by Spring Boot when we use @Autowired in our services or controllers.
// Repository interfaces enable Spring Data JPA to automatically handle the persistence layer,
// making it easier for us to interact with our database without needing to manually write SQL queries.
public interface EquipmentLogRepository extends JpaRepository<EquipmentLog, Long> {

    // Custom query to find logs by equipment name
    List<EquipmentLog> findByEquipmentName(String equipmentName);

    // Custom query to find logs by equipment condition
    List<EquipmentLog> findByEquipmentCondition(String condition);

    // Custom query to find equipment logs needing service (e.g., condition "Needs Maintenance")
    @Query("SELECT el FROM EquipmentLog el WHERE el.equipmentCondition = 'Needs Maintenance'")
    List<EquipmentLog> findNeedingServiceLogs();

    // Custom query to find logs by the last service date
    List<EquipmentLog> findEquipmentLogByLastServiceDate(LocalDateTime lastServiceDate);
}