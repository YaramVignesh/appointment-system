package com.vignesh01.appointment_system.controller;


import com.vignesh01.appointment_system.entity.Service;
import com.vignesh01.appointment_system.repository.ServiceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceRepository serviceRepository;

    public ServiceController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @GetMapping
    public ResponseEntity<List<Service>>getAllServices(){
        return ResponseEntity.ok(serviceRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable Long id){
        return serviceRepository.findById(id).
                map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Service> createService(@RequestBody Service service){
        Service savedService = serviceRepository.save(service);
        return new ResponseEntity<>(savedService, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(@PathVariable Long id, @RequestBody Service serviceDetails) {
        return serviceRepository.findById(id)
                .map(existingService -> {
                    existingService.setName(serviceDetails.getName());
                    existingService.setDescription(serviceDetails.getDescription());
                    existingService.setDurationInMinutes(serviceDetails.getDurationInMinutes());
                    existingService.setPrice(serviceDetails.getPrice());
                    Service updatedService = serviceRepository.save(existingService);
                    return ResponseEntity.ok(updatedService);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        if (!serviceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        serviceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
