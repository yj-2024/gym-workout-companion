package com.capstone.gym_workout_companion.repository;

// BookingRepository.java

import com.capstone.gym_workout_companion.model.Booking;
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
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Custom query to find bookings by ID
   // Optional<Booking> findById(Long id);

    // Custom query to find bookings by user ID
    List<Booking> findByUserId(Long userId);

    // Custom query to find bookings by class ID
    List<Booking> findByClassId(Long classId);

    // Custom query to find bookings by both user and class ID (useful for checking if a user has already booked a class)
    List<Booking> findByUserIdAndClassId(Long userId, Long classId);

    // Custom query to find bookings by date (assuming a field named 'date' exists in the Booking entity)
    List<Booking> findBookingByBookingDate(LocalDateTime bookingDate);

    // Custom query to count the number of bookings for a particular workout classes by class id
    //List<Booking> countByWorkoutClass_Id(Long workoutClassId);

    // Custom query to count the number of bookings for a particular workout class
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.workoutClass.id = :workoutClassId")
    int countByWorkoutClass_Id(Long workoutClassId);

    // Custom query to find bookings by customer name
    List<Booking> findByCustomerName(String customerName);

    // Custom query to find bookings by workout class ID
    List<Booking> findByWorkoutClass_Id(Long workoutClassId);

    // Find bookings by date range
    @Query("SELECT b FROM Booking b WHERE b.bookingDate BETWEEN :startDate AND :endDate")
    List<Booking> findByBookingDateBetween(String startDate, String endDate);

    // Count bookings by class ID
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.classId = :classId")
    long countByClassId(Long classId);
}