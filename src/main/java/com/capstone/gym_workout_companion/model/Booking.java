package com.capstone.gym_workout_companion.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // Corresponding field for user ID
    private Long classId; // Corresponding field for class ID

    @ManyToOne
    @JoinColumn(name = "booking_user_id", insertable = false)
    private User user;

    @ManyToOne
    private WorkoutClass workoutClass; // This is the class the customer is booking.

    private LocalDateTime bookingDate;

    @Temporal(TemporalType.DATE)  // Store only the date (no time)
    private Date date;  // Add this field for booking date

    private String customerName; // Customer's name for the booking

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WorkoutClass getWorkoutClass() {
        return workoutClass;
    }

    public void setWorkoutClass(WorkoutClass workoutClass) {
        this.workoutClass = workoutClass;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // Getter and Setter for userId
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Getter and Setter for classId
    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}