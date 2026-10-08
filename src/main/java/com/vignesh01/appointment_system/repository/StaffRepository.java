package com.vignesh01.appointment_system.repository;

import com.vignesh01.appointment_system.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff,Long> {
    Optional<Staff> findByEmail(String email);
}
