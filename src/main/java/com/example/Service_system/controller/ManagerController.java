package com.example.Service_system.controller;
import com.example.Service_system.dto.client.ResponseClientDto;
import com.example.Service_system.dto.manager.RequestManagerDto;
import com.example.Service_system.dto.manager.RequestManagerForSignupDto;
import com.example.Service_system.dto.manager.ResponseManagerDto;
import com.example.Service_system.dto.proficient.RequestProficientDto;
import com.example.Service_system.dto.service.ResponseServiceDto;
import com.example.Service_system.dto.subService.RequestSubServiceDto;
import com.example.Service_system.entity.*;
import com.example.Service_system.model.input.Model;
import com.example.Service_system.service.ManagerService;
import jakarta.validation.Valid;
import org.dozer.DozerBeanMapper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/Manager")
public class ManagerController {

    private final ManagerService managerService;
    private final ModelMapper modelMapper;
    private final DozerBeanMapper dozerBeanMapper;

    public ManagerController(ManagerService managerService, ModelMapper modelMapper, DozerBeanMapper dozerBeanMapper) {
        this.managerService = managerService;
        this.modelMapper = modelMapper;
        this.dozerBeanMapper = dozerBeanMapper;
    }

    @RequestMapping(path = "/editSubServiceDescription", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE ,MediaType.ALL_VALUE})
    public ResponseEntity<HttpStatus> editSubServiceDescription( RequestManagerDto requestManagerDto,
                                                     RequestSubServiceDto requestSubServiceDto,
                                                                 String description){
        Manager manager = modelMapper.map(requestManagerDto, Manager.class);
        SubService subService = modelMapper.map(requestSubServiceDto, SubService.class);
        managerService.editSubServiceDescription(manager,subService,description);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/editSubServicePrice", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE ,MediaType.ALL_VALUE})
    public ResponseEntity<HttpStatus> editSubServicePrice( RequestManagerDto requestManagerDto,
                                     RequestSubServiceDto requestSubServiceDto,
                                     float price){
        Manager manager = modelMapper.map(requestManagerDto, Manager.class);
        SubService subService = modelMapper.map(requestSubServiceDto, SubService.class);
        managerService.editSubServicePrice(manager,subService,price);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/acceptProficient", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE ,MediaType.ALL_VALUE})
    public ResponseEntity<HttpStatus> acceptProficient( RequestManagerDto requestManagerDto,
                                  long id){
        Manager manager = modelMapper.map(requestManagerDto, Manager.class);
        managerService.acceptProficient(manager,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/addProficientToSubService", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE ,MediaType.ALL_VALUE})
    public ResponseEntity<HttpStatus> addProficientToSubService( RequestProficientDto requestProficientDto,
                                           RequestManagerDto requestManagerDto,
                                           RequestSubServiceDto requestSubServiceDto){
        Proficient proficient = modelMapper.map(requestProficientDto, Proficient.class);
        Manager manager = modelMapper.map(requestManagerDto, Manager.class);
        SubService subService = modelMapper.map(requestSubServiceDto, SubService.class);
        managerService.addProficientToSubService(proficient,manager,subService);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/removeProficientFromSubService", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE ,MediaType.ALL_VALUE})
    public ResponseEntity<HttpStatus> removeProficientFromSubService( RequestProficientDto requestProficientDto,
                                                RequestManagerDto requestManagerDto,
                                                RequestSubServiceDto requestSubServiceDto){
        Proficient proficient = modelMapper.map(requestProficientDto, Proficient.class);
        Manager manager = modelMapper.map(requestManagerDto, Manager.class);
        SubService subService = modelMapper.map(requestSubServiceDto, SubService.class);
        managerService.removeProficientFromSubService(proficient,manager,subService);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/filterUser")
    public ResponseEntity<List<Users>> filter(@RequestBody Model model){
        return new ResponseEntity<>( managerService.filter(model) ,HttpStatus.OK );
    }

    @PostMapping("/addManager")
    public ResponseEntity<ResponseManagerDto> saveManager(@Valid @RequestBody RequestManagerForSignupDto managerForSignupDto){
        Manager manager = modelMapper.map(managerForSignupDto, Manager.class);
        managerService.save(manager);
        ResponseManagerDto responseManagerDto = modelMapper.map(manager, ResponseManagerDto.class);
        return new ResponseEntity<>(responseManagerDto,HttpStatus.OK);
    }

    @PostMapping("/showServiceToManager")
    public ResponseEntity<List<Services>> showServiceToManager(@RequestBody RequestManagerDto requestManagerDto){
        Manager manager = modelMapper.map(requestManagerDto, Manager.class);
        List<Services> services = managerService.showServices(manager);
        return new ResponseEntity<>(services,HttpStatus.OK);
    }
}
