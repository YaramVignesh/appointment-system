package com.vignesh01.appointment_system.controller;

import com.vignesh01.appointment_system.dto.StaffRequestDTO;
import com.vignesh01.appointment_system.dto.StaffResponseDTO;
import com.vignesh01.appointment_system.service.StaffService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public ResponseEntity<List<StaffResponseDTO>> getAllStaff() {
        List<StaffResponseDTO> staffList =
                staffService.getAllStaff();

        return ResponseEntity.ok(staffList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffResponseDTO> getStaffById(
            @PathVariable Long id) {

        return staffService.getStaffById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StaffResponseDTO> createStaff(
            @RequestBody StaffRequestDTO staffRequestDTO) {

        StaffResponseDTO savedStaff =
                staffService.createStaff(staffRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedStaff);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffResponseDTO> updateStaff(
            @PathVariable Long id,
            @RequestBody StaffRequestDTO staffDetails) {

        return staffService.updateStaff(id, staffDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(
            @PathVariable Long id) {

        if (!staffService.deleteStaff(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}