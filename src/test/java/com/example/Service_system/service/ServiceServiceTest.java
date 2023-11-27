package com.example.Service_system.service;

import com.example.Service_system.entity.Manager;
import com.example.Service_system.entity.Services;
import com.example.Service_system.exception.CustomException;
import com.example.Service_system.repository.ServiceRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceServiceTest {

    private Services services;
    private Manager manager;

    @Mock
    private ServiceRepository serviceRepository;
    @Mock
    private ServiceService serviceService;

    @InjectMocks
    private ServiceService underTest;

    @BeforeEach
    void seUp() {
        services=new Services(
                1L,
                "asdfg"
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

    @Test
    void save() {
        underTest.save(services);
    }

    @Test
    void delete() throws CustomException {
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(services));
        underTest.delete(services,manager);
    }

    @Test
    void delete2() {
        assertThrows(CustomException.class,() -> underTest.delete(services,manager));
    }

    @Test
    void update() throws CustomException {
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(services));
        underTest.update(services);
    }

    @Test
    void update2() {
        assertThrows(CustomException.class,() -> underTest.update(services));
    }

    @Test
    void find() {
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(services));
        underTest.find(1);
    }

    @Test
    void serviceNames() {
        List<Services> list=new ArrayList<>();
        list.add(services);
        when(serviceRepository.findAll()).thenReturn(list);
        underTest.serviceNames();
    }

    @Test
    void addService() {
        List<Services> list=new ArrayList<>();
        list.add(services);
        when(serviceRepository.findAll()).thenReturn(list);
        assertThrows(CustomException.class,() -> underTest.addService(manager,services));
    }

    @Test
    void addService2() throws CustomException {
        Services service=new Services(
                2L,
                "defefrfv"
        );
        List<Services> list=new ArrayList<>();
        list.add(service);
        when(serviceRepository.findAll()).thenReturn(list);
        underTest.addService(manager,services);
    }
}