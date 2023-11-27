package com.example.Service_system.controller;
import com.example.Service_system.dto.manager.RequestManagerDto;
import com.example.Service_system.dto.service.RequestServiceDto;
import com.example.Service_system.dto.service.ResponseServiceDto;
import com.example.Service_system.dto.subService.RequestSubServiceDto;
import com.example.Service_system.dto.subService.RequestSubServiceForSaveDto;
import com.example.Service_system.entity.Manager;
import com.example.Service_system.entity.Services;
import com.example.Service_system.entity.SubService;
import com.example.Service_system.service.SubServiceService;
import org.dozer.DozerBeanMapper;
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
@RequestMapping("/subService")
public class SubServiceController {

    private final SubServiceService subServiceService;
    private final ModelMapper modelMapper;
    private final DozerBeanMapper dozerBeanMapper;

    public SubServiceController(SubServiceService subServiceService, ModelMapper modelMapper, DozerBeanMapper dozerBeanMapper) {
        this.subServiceService = subServiceService;
        this.modelMapper = modelMapper;
        this.dozerBeanMapper = dozerBeanMapper;
    }


    @RequestMapping(path = "/addSubService", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE ,MediaType.ALL_VALUE})
    public ResponseEntity<HttpStatus> addSubService(RequestManagerDto requestManagerDto,
                                                    RequestSubServiceForSaveDto requestSubServiceDto,
                                                    RequestServiceDto serviceDto){
        Manager manager = modelMapper.map(requestManagerDto, Manager.class);
        SubService subService = modelMapper.map(requestSubServiceDto, SubService.class);
        Services services = modelMapper.map(serviceDto, Services.class);
        subService.setService(services);
        subServiceService.addSubService(manager,subService);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
