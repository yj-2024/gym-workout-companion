package com.capstone.gym_workout_companion.service;

import com.capstone.gym_workout_companion.model.Booking;
import com.capstone.gym_workout_companion.model.WorkoutClass;
import com.capstone.gym_workout_companion.repository.BookingRepository;
import com.capstone.gym_workout_companion.repository.WorkoutClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private WorkoutClassRepository workoutClassRepository;

    // Create a new booking
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    // Get a booking by ID
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Cancel a booking by ID
    public boolean cancelBooking(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            bookingRepository.delete(booking.get());
            return true; // Successfully canceled
        }
        return false; // Booking not found
    }

    // Delete a booking by ID
    @Transactional
    public boolean deleteBooking(Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false; // If the booking doesn't exist, return false
    }

    // Create a new booking
    public Booking createBooking(Long workoutClassId, String customerName) {
        Optional<WorkoutClass> workoutClass = workoutClassRepository.findById(workoutClassId);
        if (workoutClass.isPresent()) {
            WorkoutClass classDetails = workoutClass.get();
            if (classDetails.getCapacity() > 0) { // Check if there is space in the class
                Booking newBooking = new Booking();
                newBooking.setWorkoutClass(classDetails);
                newBooking.setCustomerName(customerName);

                // Decrease the class's available capacity by 1 when a new booking is created
                classDetails.setCapacity(classDetails.getCapacity() - 1);
                workoutClassRepository.save(classDetails); // Save updated class capacity

                return bookingRepository.save(newBooking); // Save the new booking
            } else {
                throw new RuntimeException("No available capacity in the class"); // Handle case when no space is available
            }
        } else {
            throw new RuntimeException("Workout class not found"); // Handle case when workout class is not found
        }
    }

    // Get bookings by customer name
    public List<Booking> getBookingsByCustomerName(String customerName) {
        return bookingRepository.findByCustomerName(customerName);
    }

    // Get bookings by workout class ID
    public List<Booking> getBookingsByWorkoutClass(Long workoutClassId) {
        return bookingRepository.findByWorkoutClass_Id(workoutClassId);
    }

    // Update an existing booking/update booking details (e.g., customer name or workout class)
    @Transactional
    public Booking updateBooking(Long id, Long workoutClassId, String customerName) {
        Optional<Booking> existingBooking = bookingRepository.findById(id);
        if (existingBooking.isPresent()) {
            Booking booking = existingBooking.get();
            Optional<WorkoutClass> workoutClassOpt = workoutClassRepository.findById(workoutClassId);

            // Set the customer name
            booking.setCustomerName(customerName);

            // Check if the workout class is found
            if (workoutClassOpt.isPresent()) {
                WorkoutClass workoutClass = workoutClassOpt.get();                // Update the class if there's available capacity
                // Update the class if there's available capacity
                if (workoutClass.getCapacity() > 0) {
                    booking.setWorkoutClass(workoutClass);
                    booking.setCustomerName(customerName);

                    // Update class capacity (decrease by 1 as a slot is being booked)
                    workoutClass.setCapacity(workoutClass.getCapacity() - 1);
                    // Update the capacity for the new class and save changes

                    workoutClassRepository.save(workoutClass);

                    return bookingRepository.save(booking);
                } else {
                    throw new RuntimeException("New workout class has no available capacity");
                }
            } else {
                throw new RuntimeException("New workout class not found");
            }
        }
        throw new RuntimeException("Booking not found"); // Handle case when booking doesn't exist
    }

    // Check if a workout class is fully booked
    public boolean isClassFullyBooked(Long workoutClassId) {
        Optional<WorkoutClass> workoutClass = workoutClassRepository.findById(workoutClassId);
        if (workoutClass.isPresent()) {
            return workoutClass.get().getCapacity() <= 0; // If capacity is 0 or less, the class is fully booked
        }
        return false; // Class not found
    }

    // Get all bookings for a specific date
    public List<Booking> getBookingsByDate(String date) {
        return bookingRepository.findBookingByBookingDate(LocalDateTime.parse(date));
    }

    // Get all bookings for a specific user
    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    // Get all bookings for a specific class
    public List<Booking> getBookingsByClassId(Long classId) {
        return bookingRepository.findByClassId(classId);
    }

    // Get all bookings within a specific date range
    public List<Booking> getBookingsByDateRange(String startDate, String endDate) {
        return bookingRepository.findByBookingDateBetween(startDate, endDate);
    }

    // Get booking count for a specific class (e.g., check if class is full)
    public long getBookingCountForClass(Long classId) {
        return bookingRepository.countByClassId(classId);
    }

    // Check if a user has already booked a class (prevent multiple bookings for the same class)
    public boolean isUserBookedForClass(Long userId, Long classId) {
        List<Booking> bookings = bookingRepository.findByUserIdAndClassId(userId, classId);
        return !bookings.isEmpty(); // Returns true if the user has already booked the class
    }

    // Get the total number of bookings for a specific workout class
    public int getTotalBookingsForClass(Long workoutClassId) {
        return bookingRepository.countByWorkoutClass_Id(workoutClassId);
    }

    // Check availability of a class based on capacity
    public boolean isClassAvailable(Long workoutClassId) {
        Optional<WorkoutClass> workoutClass = workoutClassRepository.findById(workoutClassId);
        return workoutClass.map(classDetails -> classDetails.getCapacity() > 0).orElse(false);
    }

    // This can be used if you want to "restore" capacity after a booking cancellation (optional)
    public void restoreCapacity(Long workoutClassId) {
        Optional<WorkoutClass> workoutClass = workoutClassRepository.findById(workoutClassId);
        workoutClass.ifPresent(classDetails -> {
            classDetails.setCapacity(classDetails.getCapacity() + 1); // Increase capacity by 1
            workoutClassRepository.save(classDetails); // Save the updated class
        });
    }
}
