package com.example.Service_system.service;
import com.example.Service_system.entity.Manager;
import com.example.Service_system.entity.Services;
import com.example.Service_system.exception.CustomException;
import com.example.Service_system.repository.ServiceRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceService  {
    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }


    public void save(Services service) {
        serviceRepository.save(service);
    }


    public void delete(Services service, Manager manager)  {
        if (serviceRepository.findById(service.getId()).isPresent()) {
            serviceRepository.delete(service);
        }else
            throw new CustomException("error");
    }


    public void update(Services service) {
        Optional<Services> byId = serviceRepository.findById(service.getId());
        if (byId.isPresent()) {
            serviceRepository.save(service);
        }else
            throw new CustomException("this service not found");


    }


    public Optional<Services> find(long id) {
        return serviceRepository.findById(id);
    }

    public List<Services> findAll(){
        return serviceRepository.findAll();
    }

    public List<String> serviceNames(){
        return serviceRepository.findAll().stream().map(Services::getName).toList();
    }

    public void addService(Manager manager, Services service) {
                List<String> serviceNames = serviceNames();
                if (serviceNames.contains(service.getName()))
                    throw new CustomException(" this name is duplicate please choose new name for service ");
                else
                    save(service);

    }
}
