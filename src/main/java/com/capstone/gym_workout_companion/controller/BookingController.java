package com.capstone.gym_workout_companion.controller;

import com.capstone.gym_workout_companion.service.BookingService;
import com.capstone.gym_workout_companion.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// BookingController used for implementing booking functionality for workout classes)
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Create a booking for a workout class
    @PostMapping("/create-booking")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking createdBooking = bookingService.createBooking(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }

    // Get all bookings
    @GetMapping("/all-bookings")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    // Get a booking by ID
    @GetMapping("/booking/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        return booking != null ? ResponseEntity.ok(booking) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/user/{userId}/bookings")
    public List<Booking> getBookingsByUser(@PathVariable Long userId) {
        return bookingService.getBookingsByUserId(userId);
    }

    @PutMapping("/booking/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking updatedBooking) {
        Booking updatedBookingResponse = bookingService.updateBooking(id, updatedBooking.getClassId(), updatedBooking.getCustomerName());
        return updatedBookingResponse != null ?
                ResponseEntity.ok(updatedBookingResponse) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Cancel a booking by ID
    @DeleteMapping("/cancel-booking/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        boolean canceled = bookingService.cancelBooking(id);
        return canceled ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}