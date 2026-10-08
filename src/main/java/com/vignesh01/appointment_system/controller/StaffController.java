package com.vignesh01.appointment_system.controller;

import com.vignesh01.appointment_system.entity.Staff;
import com.vignesh01.appointment_system.repository.StaffRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;

    public StaffController(StaffRepository staffRepository, PasswordEncoder passwordEncoder) {
        this.staffRepository = staffRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<Staff>> getAllStaff() {
        List<Staff> staffList = staffRepository.findAll();
        return ResponseEntity.ok(staffList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Long id) {
        return staffRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Staff> createStaff(@RequestBody Staff staff) {
        staff.setPassword(passwordEncoder.encode(staff.getPassword()));
        Staff savedStaff = staffRepository.save(staff);
        return new ResponseEntity<>(savedStaff, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable Long id, @RequestBody Staff staffDetails) {
        return staffRepository.findById(id)
                .map(existingStaff -> {
                    existingStaff.setFirstName(staffDetails.getFirstName());
                    existingStaff.setLastName(staffDetails.getLastName());
                    existingStaff.setEmail(staffDetails.getEmail());
                    Staff updatedStaff = staffRepository.save(existingStaff);
                    return ResponseEntity.ok(updatedStaff);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        if (!staffRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        staffRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
