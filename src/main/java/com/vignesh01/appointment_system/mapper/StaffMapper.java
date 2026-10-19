package com.vignesh01.appointment_system.mapper;

import com.vignesh01.appointment_system.dto.StaffRequestDTO;
import com.vignesh01.appointment_system.dto.StaffResponseDTO;
import com.vignesh01.appointment_system.entity.Staff;
import org.springframework.stereotype.Component;

@Component
public class StaffMapper {

    public Staff toEntity(StaffRequestDTO dto) {
        Staff staff = new Staff();
        staff.setFirstName(dto.getFirstName());
        staff.setLastName(dto.getLastName());
        staff.setEmail(dto.getEmail());
        staff.setPassword(dto.getPassword());
        return staff;
    }
    public StaffResponseDTO toDto(Staff entity) {
        StaffResponseDTO dto = new StaffResponseDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        return dto;
    }
}
