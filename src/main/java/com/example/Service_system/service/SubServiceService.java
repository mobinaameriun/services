package com.example.Service_system.service;
import com.example.Service_system.entity.Manager;
import com.example.Service_system.entity.Services;
import com.example.Service_system.entity.SubService;
import com.example.Service_system.exception.CustomException;
import com.example.Service_system.repository.ServiceRepository;
import com.example.Service_system.repository.SubServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubServiceService  {
    private final SubServiceRepository subServiceRepository;
    private final ServiceRepository serviceRepository;

    @Autowired
    public SubServiceService(SubServiceRepository subServiceRepository,ServiceRepository serviceRepository) {
        this.subServiceRepository = subServiceRepository;
        this.serviceRepository=serviceRepository;
    }



    private void save(SubService subService) {
        subServiceRepository.save(subService);
    }


    public void delete(SubService subService) throws CustomException {
        Optional<SubService> byId = subServiceRepository.findById(subService.getId());
        if (byId.isPresent()) {
            subServiceRepository.delete(subService);
        }else
            throw new CustomException("this sub service is not found");
    }


    public void update(SubService subService) {
        Optional<SubService> byId = subServiceRepository.findById(subService.getId());
        if (byId.isPresent()) {
            subServiceRepository.save(subService);
        }else
            throw new CustomException("this sub service is not found");
    }


    public Optional<SubService> find(long id) throws CustomException {
        Optional<SubService> byId = subServiceRepository.findById(id);
        if(byId.isPresent()) {
            return subServiceRepository.findById(id);
        }else
            throw new CustomException("error");
    }

    public List<String> listOfSubServiceNames() {
        return subServiceRepository.findAll().stream().map(SubService::getName).collect(Collectors.toList());

    }

    public void addSubService(Manager manager, SubService subService) {
            List<String> subServiceNames = listOfSubServiceNames();
            if (subServiceNames.contains(subService.getName())) {
                throw new CustomException("this name is duplicate please choose new name for sub service");
            } else {
                Optional<Services> service = serviceRepository.findById(subService.getService().getId());
                if (service.isPresent()) {
                    save(subService);
                } else
                    throw new CustomException("service not found");
            }

    }
}
