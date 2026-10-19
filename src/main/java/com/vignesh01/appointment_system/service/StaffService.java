package com.vignesh01.appointment_system.service;

import com.vignesh01.appointment_system.dto.StaffRequestDTO;
import com.vignesh01.appointment_system.dto.StaffResponseDTO;
import com.vignesh01.appointment_system.entity.Staff;
import com.vignesh01.appointment_system.mapper.StaffMapper;
import com.vignesh01.appointment_system.repository.StaffRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final StaffMapper staffMapper;

    public StaffService(
            StaffRepository staffRepository,
            PasswordEncoder passwordEncoder,
            StaffMapper staffMapper) {
        this.staffRepository = staffRepository;
        this.passwordEncoder = passwordEncoder;
        this.staffMapper = staffMapper;
    }

    public List<StaffResponseDTO> getAllStaff() {
        return staffRepository.findAll()
                .stream()
                .map(staffMapper::toDto)
                .toList();
    }

    public Optional<StaffResponseDTO> getStaffById(Long id) {
        return staffRepository.findById(id)
                .map(staffMapper::toDto);
    }

    public StaffResponseDTO createStaff(StaffRequestDTO staffRequestDto) {
        Staff staff = staffMapper.toEntity(staffRequestDto);

        staff.setPassword(passwordEncoder.encode(staff.getPassword()));

        Staff savedStaff = staffRepository.save(staff);

        return staffMapper.toDto(savedStaff);
    }

    public Optional<StaffResponseDTO> updateStaff(
            Long id, StaffRequestDTO staffDetails) {

        return staffRepository.findById(id)
                .map(existingStaff -> {
                    existingStaff.setFirstName(staffDetails.getFirstName());
                    existingStaff.setLastName(staffDetails.getLastName());
                    existingStaff.setEmail(staffDetails.getEmail());

                    Staff updatedStaff = staffRepository.save(existingStaff);

                    return staffMapper.toDto(updatedStaff);
                });
    }

    public boolean deleteStaff(Long id) {
        if (!staffRepository.existsById(id)) {
            return false;
        }

        staffRepository.deleteById(id);
        return true;
    }
}