package com.vignesh01.appointment_system.service;

import com.vignesh01.appointment_system.entity.Staff;
import com.vignesh01.appointment_system.repository.StaffRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;

    public StaffService(StaffRepository staffRepository, PasswordEncoder passwordEncoder) {
        this.staffRepository = staffRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Staff> getAllStaff(){
        return staffRepository.findAll();
    }
    public Optional<Staff> getStaffById(Long id){
        return staffRepository.findById(id);
    }
    public Staff createStaff(Staff staff){

        staff.setPassword(passwordEncoder.encode(staff.getPassword()));
        return staffRepository.save(staff);
    }

    public Optional<Staff> updateStaff(Long id,Staff staffDetails){
        return staffRepository.findById(id)
                .map(existingStaff ->{
                    existingStaff.setFirstName(staffDetails.getFirstName());
                    existingStaff.setLastName(staffDetails.getLastName());
                    existingStaff.setEmail(staffDetails.getEmail());
                    return staffRepository.save(existingStaff);
                });

    }
    public boolean deleteStaff(Long id){
        if(!staffRepository.existsById(id)){
            return false;
        }
        staffRepository.deleteById(id);
        return true;
    }

}
