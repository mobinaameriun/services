package com.example.Service_system.service;
import com.example.Service_system.entity.Manager;
import com.example.Service_system.entity.Proficient;
import com.example.Service_system.entity.Services;
import com.example.Service_system.entity.SubService;
import com.example.Service_system.enumration.ProficientStatus;
import com.example.Service_system.exception.CustomException;
import com.example.Service_system.repository.ManagerRepository;
import com.example.Service_system.repository.ProficientRepository;
import com.example.Service_system.repository.ServiceRepository;
import com.example.Service_system.repository.SubServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManagerServiceTest {
    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private ServiceRepository serviceRepository;
    @Mock
    private SubServiceRepository subServiceRepository;
    @Mock
    private ProficientRepository proficientRepository;
    @Mock
    private ProficientService proficientService;
    @InjectMocks
    private ManagerService underTest;

    private Manager manager;
    private Services service;
    private SubService subService;
    private Proficient proficient;

    @BeforeEach
    void setUp() {
        manager = new Manager(
                1L,
                "mobina",
                "asf",
                "mobina@gmail.com",
                "mobina12345amn",
                LocalDateTime.now()
        );
        service = new Services(
                1L,
                "abcd"
        );
        subService = new SubService(
                1L,
                "asdfg",
                123f,
                "jgfglm",
                service
        );
        proficient = new Proficient(
                1L,
                "mobina",
                "asf",
                "mobina@gmail.com",
                "mobina12345amn",
                LocalDateTime.now(),
                ProficientStatus.ACCEPTED
        );
    }


    @Test
    void save() {
        when(managerRepository.save(any())).thenReturn(manager);
        underTest.save(manager);
        verify(managerRepository,times(1)).save(manager);
    }

    @Test
    void delete() {
        underTest.delete(manager);
        verify(managerRepository,times(1)).delete(manager);
    }

    @Test
    void update() {
        assertThrows(CustomException.class, () -> underTest.update(manager));
    }
    @Test
    void update2() throws CustomException {
        when(managerRepository.findById(1L)).thenReturn(Optional.of(manager));
        underTest.update(manager);
    }

    @Test
    void find() {
        when(managerRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Manager> manager1 = underTest.find(2);
        assertTrue(manager1.isEmpty());
    }

    @Test
    void showServices() {
        when(serviceRepository.findAll()).thenReturn(List.of(service));
        List<Services> services = underTest.showServices(manager);
        assertFalse(services.isEmpty());
    }

    @Test
    void editSubServiceDescription() throws CustomException {
        when(subServiceRepository.findById(1L)).thenReturn(Optional.of(subService));
        underTest.editSubServiceDescription(manager,subService,"jggkm");
    }
    @Test
    void editSubServiceDescription2() {
        assertThrows(CustomException.class,() -> underTest.editSubServiceDescription(manager,subService,"jggkm"));
    }

    @Test
    void editSubServicePrice() throws CustomException {
        when(subServiceRepository.findById(1L)).thenReturn(Optional.of(subService));
        underTest.editSubServicePrice(manager,subService,124);
    }
    @Test
    void editSubServicePrice2() {
        assertThrows(CustomException.class,() -> underTest.editSubServicePrice(manager,subService,1234));
    }

    @Test
    void acceptProficient() throws CustomException {
        when(proficientRepository.findById(1L)).thenReturn(Optional.of(proficient));
        underTest.acceptProficient(manager,1);
    }
    @Test
    void acceptProficient2() {
        assertThrows(CustomException.class,() -> underTest.acceptProficient(manager,1));
    }

    @Test
    void addProficientToSubService() throws CustomException {
        when(subServiceRepository.findById(1L)).thenReturn(Optional.of(subService));
        underTest.addProficientToSubService(proficient,manager,subService);
    }

    @Test
    void addProficientToSubService2() {
        assertThrows(CustomException.class,() -> underTest.addProficientToSubService(proficient,manager,subService));
    }

    @Test
    void addProficientToSubService3() {
        proficient.setStatus(ProficientStatus.NOT_CONFIRMED);
        assertThrows(CustomException.class,() -> underTest.addProficientToSubService(proficient,manager,subService));
    }

    @Test
    void removeProficientFromSubService() throws CustomException {
        proficient.setStatus(ProficientStatus.ACCEPTED);
        proficient.setSubServiceList(subService);
        when(subServiceRepository.findById(1L)).thenReturn(Optional.of(subService));
        underTest.removeProficientFromSubService(proficient,manager,subService);
    }

    @Test
    void removeProficientFromSubService2() throws CustomException {
        when(subServiceRepository.findById(1L)).thenReturn(Optional.of(subService));
        assertThrows(CustomException.class,
                () -> underTest.removeProficientFromSubService(proficient,manager,subService));
    }
    @Test
    void removeProficientFromSubService3() {
        proficient.setStatus(ProficientStatus.NOT_CONFIRMED);
        assertThrows(CustomException.class,
                () -> underTest.removeProficientFromSubService(proficient,manager,subService));
    }
    @Test
    void removeProficientFromSubService4() throws CustomException {
        assertThrows(CustomException.class,
                () -> underTest.removeProficientFromSubService(proficient,manager,subService));
    }

}