package com.capstone.gym_workout_companion.service;

import com.capstone.gym_workout_companion.model.EquipmentLog;
import com.capstone.gym_workout_companion.repository.EquipmentLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class EquipmentLogService {

    @Autowired
    private EquipmentLogRepository equipmentLogRepository;

    // Create a new equipment log entry
    public EquipmentLog createEquipmentLog(EquipmentLog equipmentLog) {
        return equipmentLogRepository.save(equipmentLog);
    }

    // Get all equipment logs
    public List<EquipmentLog> getAllEquipmentLogs() {
        return equipmentLogRepository.findAll();
    }

    // Get equipment log by ID
    public EquipmentLog getEquipmentLogById(Long id) {
        return equipmentLogRepository.findById(id).orElse(null);
    }

    // Update an equipment log entry (e.g., update equipment condition or service date)
    @Transactional
    public EquipmentLog updateEquipmentLog(Long id, EquipmentLog updatedLog) {
        Optional<EquipmentLog> existingLogOpt = equipmentLogRepository.findById(id);
        if (existingLogOpt.isPresent()) {
            EquipmentLog existingLog = existingLogOpt.get();
            existingLog.setEquipmentName(updatedLog.getEquipmentName());
            existingLog.setStatus(updatedLog.getStatus());
            existingLog.setDate(updatedLog.getDate());
            existingLog.setNotes(updatedLog.getNotes());
            return equipmentLogRepository.save(existingLog);
        }
        return null; // If the log doesn't exist, return null or throw an exception
    }

    // Delete an equipment log by ID
    @Transactional
    public boolean deleteEquipmentLog(Long id) {
        if (equipmentLogRepository.existsById(id)) {
            equipmentLogRepository.deleteById(id);
            return true;
        }
        return false; // If the equipment log doesn't exist, return false
    }

    // Get all equipment logs with a specific condition (e.g., "Needs Maintenance")
    public List<EquipmentLog> getEquipmentLogsByCondition(String condition) {
        return equipmentLogRepository.findByEquipmentCondition(condition);
    }

    // Get all equipment logs that need service (based on a certain condition)
    public List<EquipmentLog> getEquipmentLogsNeedingService() {
        return equipmentLogRepository.findNeedingServiceLogs();
    }

    // Get count of equipment logs for reporting purposes
    public long getEquipmentLogCount() {
        return equipmentLogRepository.count();
    }

    // Update service date for a specific equipment log
    @Transactional
    public boolean updateServiceDate(Long id, String newServiceDate) {
        Optional<EquipmentLog> logOpt = equipmentLogRepository.findById(id);
        if (logOpt.isPresent()) {
            EquipmentLog equipmentLog = logOpt.get();

            // Convert newServiceDate (String) to LocalDateTime
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // Adjust format as needed
                LocalDateTime serviceDate = LocalDateTime.parse(newServiceDate, formatter);
                equipmentLog.setLastServiceDate(serviceDate);
                equipmentLogRepository.save(equipmentLog);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false; // If parsing fails, return false
            }
        }
        return false; // If the log doesn't exist, return false
    }
}