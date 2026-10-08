package com.vignesh01.appointment_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vignesh01.appointment_system.entity.Service;

@Repository
public interface ServiceRepository  extends JpaRepository<Service,Long> {
}
