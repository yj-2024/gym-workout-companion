package com.capstone.gym_workout_companion.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
public class EquipmentLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private String equipmentName; REWRITTEN
    //private LocalDateTime lastServiceDate; REWRITTEN
    private String condition;
    private String equipmentCondition; // This should be declared before the getter and setter methods


    @Column(name = "last_service_date")
    private LocalDateTime lastServiceDate;

    @Column(name = "equipment_name")
    private String equipmentName;
    private String status;
    private Date date;
    private String notes;


    public EquipmentLog() {
    }

    public EquipmentLog(Long id, LocalDateTime lastServiceDate, String condition, String equipmentName) {
        this.id = id;
        this.lastServiceDate = lastServiceDate;
        this.condition = condition;
        this.equipmentName = equipmentName;
    }

}