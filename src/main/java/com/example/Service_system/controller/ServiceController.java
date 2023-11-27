package com.example.Service_system.controller;
import com.example.Service_system.dto.manager.RequestManagerDto;
import com.example.Service_system.dto.service.RequestServiceDto;
import com.example.Service_system.dto.service.RequestServiceForAddDto;
import com.example.Service_system.dto.service.ResponseServiceDto;
import com.example.Service_system.entity.Manager;
import com.example.Service_system.entity.Services;
import com.example.Service_system.service.ServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/service")
public class ServiceController {

    private final ServiceService serviceService;
    private final ModelMapper modelMapper;

    public ServiceController(ServiceService serviceService, ModelMapper modelMapper) {
        this.serviceService = serviceService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(path = "/deleteService", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE ,MediaType.ALL_VALUE})
    public ResponseEntity<HttpStatus> delete( RequestServiceDto requestServiceDto,
                                              RequestManagerDto requestManagerDto){
        Services services = modelMapper.map(requestServiceDto, Services.class);
        Manager manager = modelMapper.map(requestManagerDto, Manager.class);
        serviceService.delete(services,manager);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/saveService", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE ,MediaType.ALL_VALUE})
    public ResponseEntity<HttpStatus> addService( RequestManagerDto requestManagerDto,
                                                  RequestServiceForAddDto requestServiceDto){
        Manager manager = modelMapper.map(requestManagerDto, Manager.class);
        Services services = modelMapper.map(requestServiceDto, Services.class);
        serviceService.addService(manager,services);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
