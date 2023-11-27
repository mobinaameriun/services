package com.example.Service_system.service;
import com.example.Service_system.entity.Manager;
import com.example.Service_system.entity.Services;
import com.example.Service_system.entity.SubService;
import com.example.Service_system.exception.CustomException;
import com.example.Service_system.repository.ServiceRepository;
import com.example.Service_system.repository.SubServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SubServiceServiceTest {

    private SubService subService;
    private Services service;
    private Manager manager;

    @BeforeEach
    void setUp(){
        service = new Services(
                1L,
                "abcd"
        );
        subService=new SubService(
                1L,
                "asdfg",
                123f,
                "mcdmkmfjd",
                service
        );
        manager = new Manager(
                1L,
                "mobina",
                "asf",
                "mobina@gmail.com",
                "mobina12345amn",
                LocalDateTime.now()
        );
    }

    @Mock
    private SubServiceRepository subServiceRepository;
    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private SubServiceService underTest;


    @Test
    void save() {
        underTest.save(subService);
    }

    @Test
    void delete() throws CustomException {
        when(subServiceRepository.findById(1L)).thenReturn(Optional.of(subService));
        underTest.delete(subService);
    }

    @Test
    void delete2() {
        assertThrows(CustomException.class,() ->underTest.delete(subService));
    }

    @Test
    void update() throws CustomException {
        when(subServiceRepository.findById(1L)).thenReturn(Optional.of(subService));
        underTest.update(subService);
    }
    @Test
    void update2() {
        assertThrows(CustomException.class,() ->underTest.update(subService));
    }

    @Test
    void find() throws CustomException {
        when(subServiceRepository.findById(1L)).thenReturn(Optional.of(subService));
        underTest.find(1);
    }

    @Test
    void find2() {
        assertThrows(CustomException.class,() ->underTest.find(1));
    }

    @Test
    void listOfSubServiceNames() {
        List<SubService> subServiceList=new ArrayList<>();
        subServiceList.add(subService);
        when(subServiceRepository.findAll()).thenReturn(subServiceList);
        underTest.listOfSubServiceNames();
    }

    @Test
    void addSubService() throws CustomException {
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));
        underTest.addSubService(manager,subService);
    }

    @Test
    void addSubService2() {
        List<SubService> subServiceList=new ArrayList<>();
        subServiceList.add(subService);
        when(subServiceRepository.findAll()).thenReturn(subServiceList);
        assertThrows(CustomException.class,()-> underTest.addSubService(manager,subService));
    }

    @Test
    void addSubService3() {
        when(serviceRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CustomException.class,() -> underTest.addSubService(manager,subService));
    }
}