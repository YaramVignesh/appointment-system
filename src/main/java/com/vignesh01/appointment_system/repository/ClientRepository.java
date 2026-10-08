package com.vignesh01.appointment_system.repository;

import com.vignesh01.appointment_system.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Long> {
    Optional<Client> findByEmail(String email);
}
