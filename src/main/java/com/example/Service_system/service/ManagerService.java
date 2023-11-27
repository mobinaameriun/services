package com.example.Service_system.service;

import com.example.Service_system.entity.*;
import com.example.Service_system.enumration.ProficientStatus;
import com.example.Service_system.exception.CustomException;
import com.example.Service_system.exception.NotFoundException;
import com.example.Service_system.model.input.Model;
import com.example.Service_system.repository.ManagerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final ServiceService serviceService;
    private final SubServiceService subServiceService;
    private final ProficientService proficientService;


    public ManagerService(ManagerRepository managerRepository, ServiceService serviceService, SubServiceService subServiceService, ProficientService proficientService) {
        this.managerRepository = managerRepository;
        this.serviceService = serviceService;
        this.subServiceService = subServiceService;
        this.proficientService = proficientService;
    }

    public void save(Manager manager) {
        manager.setSignupDateTime(LocalDateTime.now());
        try {
            managerRepository.save(manager);
        } catch (Exception e) {
            throw new CustomException("this information invalid");
        }
    }

    public void delete(Manager manager) {
        try {
            managerRepository.delete(manager);
        } catch (Exception e) {
            throw new NotFoundException("this manager not found");
        }


    }

    public void update(Manager manager) {
        if (managerRepository.findById(manager.getId()).isPresent())
            managerRepository.save(manager);
        else
            throw new CustomException("this user not found ");
    }


    public Optional<Manager> find(long id) {
        return managerRepository.findById(id);
    }

    public List<Services> showServices(Manager manager) {
        return serviceService.findAll();
    }

    public void editSubServiceDescription(Manager manager, SubService subService, String description) {
        Optional<SubService> subService1 = subServiceService.find(subService.getId());
        if (subService1.isPresent()) {
            subService1.get().setDescription(description);
            subServiceService.update(subService1.get());
        } else
            throw new CustomException("this sub service is not found");

    }

    public void editSubServicePrice(Manager manager, SubService subService, float price) {
        Optional<SubService> subService1 = subServiceService.find(subService.getId());
        if (subService1.isPresent()) {
            subService1.get().setBasePrice(price);
            subServiceService.update(subService1.get());
        } else
            throw new CustomException("this sub service is not found");

    }

    public void acceptProficient(Manager manager, long id) {
        Optional<Proficient> proficient = proficientService.find(id);
        if (proficient.isPresent()) {
            proficient.get().setStatus(ProficientStatus.ACCEPTED);
            proficientService.update(proficient.get());
        } else
            throw new CustomException("this proficient not found");
    }

    public void addProficientToSubService(Proficient proficient, Manager manager, SubService subService) {
        Optional<Proficient> proficient1 = proficientService.find(proficient.getId());
        if (proficient1.isEmpty()) {
            throw new CustomException("Proficient not found");
        }
        if (proficient1.get().getStatus().equals(ProficientStatus.NOT_CONFIRMED))
            throw new CustomException("First, you must confirm this person's access to the system and then add her to the sub-services");
        else {
            Optional<SubService> subService1 = subServiceService.find(subService.getId());
            if (subService1.isPresent()) {
                subService1.get().addProficient(proficient1.get());
                subServiceService.update(subService1.get());
            } else
                throw new CustomException("this sub-service is not found");

        }
    }

    @Transactional
    public void removeProficientFromSubService(Proficient proficient, Manager manager, SubService subService) {
        find(manager.getId());
         proficient = proficientService.find(proficient.getId()).get();
        if (proficient.getStatus().equals(ProficientStatus.NOT_CONFIRMED))
            throw new CustomException("First, you must confirm this person's access to the system and then add her to the sub-services");
         subService = subServiceService.find(subService.getId()).get();
        List<SubService> subServiceList = proficient.getSubServiceList();
            if (subServiceList.contains(subService)) {
                proficient.getSubServiceList().remove(subService);
                proficientService.update(proficient);
                subService.getProficientList().remove(proficient);
                subServiceService.update(subService);
            } else
                throw new CustomException("The sub-services of this specialist are not included in this sub-service");

    }

    @Transactional
    public List<Users> filter(Model model) {
        return managerRepository.filter(model.getFirstName()
                , model.getLastName()
                , model.getEmailAddress());
    }

    public Optional<Manager> findManagerByEmailAddressAndPassword(String emailAddress, String password) {
        return managerRepository.findManagerByEmailAddressAndPassword(emailAddress, password);
    }
}

