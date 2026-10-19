package com.vignesh01.appointment_system.service;


import com.vignesh01.appointment_system.entity.Service;
import com.vignesh01.appointment_system.repository.ServiceRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceService {
    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }
    public Optional<Service> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    public Service createService(Service service) {
        return serviceRepository.save(service);
    }

    public Optional<Service> updateService(Long id, Service serviceDetails) {
        return serviceRepository.findById(id)
                .map(existingService -> {
                    existingService.setName(serviceDetails.getName());
                    existingService.setDescription(serviceDetails.getDescription());
                    existingService.setDurationInMinutes(serviceDetails.getDurationInMinutes());
                    existingService.setPrice(serviceDetails.getPrice());

                    return serviceRepository.save(existingService);
                });
    }

    public boolean deleteService(Long id) {
        if (!serviceRepository.existsById(id)) {
            return false;
        }
        serviceRepository.deleteById(id);
        return true;
    }
}
