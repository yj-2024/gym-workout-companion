package com.capstone.gym_workout_companion.controller;

import com.capstone.gym_workout_companion.model.EquipmentLog;
import com.capstone.gym_workout_companion.service.EquipmentLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/equipment-log")
public class EquipmentLogController {

    @Autowired
    private EquipmentLogService equipmentLogService;

    // Show the equipment-log page
    @GetMapping()
    public String showEquipmentLogForm(Model model) {
        return "equipment-log";  // This corresponds to equipment-log.html in the templates folder
    }

    @GetMapping("/confirmation")
    public String showEquipmentLogConfirmation(Model model) {
        return "equipment-log-confirmation-page";  // This corresponds to equipment-log.html in the templates folder
    }


    // Create a new Equipment Log
    @PostMapping("/create-equipment-log")
    public ResponseEntity<EquipmentLog> createEquipmentLog(@RequestBody EquipmentLog equipmentLog) {
        EquipmentLog createdLog = equipmentLogService.createEquipmentLog(equipmentLog);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLog);
    }

    // Get all Equipment Logs
    @GetMapping("/equipment-logs")
    public List<EquipmentLog> getAllEquipmentLogs() {
        return equipmentLogService.getAllEquipmentLogs();
    }

    // Get Equipment Log by ID
    @GetMapping("/equipment-log/{id}")
    public EquipmentLog getEquipmentLogById(@PathVariable Long id) {
        return equipmentLogService.getEquipmentLogById(id);
    }

    // Update Equipment Log
    @PutMapping("/equipment-log/{id}")
    public ResponseEntity<EquipmentLog> updateEquipmentLog(@PathVariable Long id, @RequestBody EquipmentLog updatedLog) {
        EquipmentLog updatedEquipmentLog = equipmentLogService.updateEquipmentLog(id, updatedLog);
        return updatedEquipmentLog != null ?
                ResponseEntity.ok(updatedEquipmentLog) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Delete Equipment Log by ID
    @DeleteMapping("/equipment-log/{id}")
    public ResponseEntity<Void> deleteEquipmentLog(@PathVariable Long id) {
        return equipmentLogService.deleteEquipmentLog(id) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}