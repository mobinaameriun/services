package com.example.Service_system.controller;

import com.example.Service_system.dto.client.ResponseClientDto;
import com.example.Service_system.dto.manager.RequestManagerDto;
import com.example.Service_system.dto.offer.RequestOfferDto;
import com.example.Service_system.dto.order.RequestOrderDto;
import com.example.Service_system.dto.order.ResponseOrderDto;
import com.example.Service_system.dto.proficient.RequestProficientDto;
import com.example.Service_system.dto.proficient.RequestProficientForSignupDto;
import com.example.Service_system.dto.proficient.ResponseProficientDto;
import com.example.Service_system.dto.subService.RequestSubServiceDto;
import com.example.Service_system.dto.subService.ResponseSubServiceDto;
import com.example.Service_system.entity.*;
import com.example.Service_system.model.response.ResponseModel;
import com.example.Service_system.service.ProficientService;
import jakarta.validation.Valid;
import org.dozer.DozerBeanMapper;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/proficient")
public class ProficientController {

    private final ProficientService proficientService;
    private final ModelMapper modelMapper;
    private final DozerBeanMapper dozerBeanMapper;

    public ProficientController(ProficientService proficientService, ModelMapper modelMapper, DozerBeanMapper dozerBeanMapper) {
        this.proficientService = proficientService;
        this.modelMapper = modelMapper;
        this.dozerBeanMapper = dozerBeanMapper;

        Converter<String, LocalDateTime> converter = new AbstractConverter<String, LocalDateTime>() {
            @Override
            protected LocalDateTime convert(String s) {
                if (s == null)
                    return null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                return LocalDateTime.parse(s, formatter);
            }
        };
        Converter<String, Time> timeConverter = new AbstractConverter<String, Time>() {
            @Override
            protected Time convert(String s) {
                if (s == null)
                    return null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                return Time.valueOf(LocalTime.parse(s, formatter));
            }
        };
        modelMapper.addConverter(converter);
        modelMapper.addConverter(timeConverter);
    }

    @RequestMapping(path = "/saveProficient", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.ALL_VALUE})
    public ResponseEntity<ResponseProficientDto> save(@Valid RequestProficientForSignupDto proficientForSignupDto,
                                                      @RequestPart MultipartFile picture) {
        Proficient proficient = modelMapper.map(proficientForSignupDto, Proficient.class);
        Proficient proficient1 = proficientService.signupProficients(proficient, picture);
        ResponseProficientDto responseProficientDto = modelMapper.map(proficient1, ResponseProficientDto.class);
        return new ResponseEntity<>(responseProficientDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ResponseClientDto> deleteUser(@PathVariable("id") long id, @RequestBody RequestManagerDto requestManagerDto) {
        Manager manager = modelMapper.map(requestManagerDto, Manager.class);
        proficientService.deleteProficient(manager, id);
        ResponseClientDto responseClientDto = modelMapper.map(manager, ResponseClientDto.class);
        return new ResponseEntity<>(responseClientDto, HttpStatus.OK);
    }

    @RequestMapping(path = "/submitOffer", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.ALL_VALUE})
    public ResponseEntity<ResponseModel> recordOffers(RequestProficientDto requestProficientDto,
                                                      RequestOrderDto requestOrderDto,
                                                      RequestOfferDto requestOfferDto) {
        Proficient proficient = modelMapper.map(requestProficientDto, Proficient.class);
        Orders orders = modelMapper.map(requestOrderDto, Orders.class);
        Offers offers = modelMapper.map(requestOfferDto, Offers.class);
        proficientService.recordOffers(proficient, orders, offers);
        return new ResponseEntity<>(new ResponseModel("this offer recorded"), HttpStatus.OK);
    }


    @RequestMapping(path = "/showOrdersOfSubService", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.ALL_VALUE})
    public ResponseEntity<List<ResponseOrderDto>> showOrdersSubServiceToProficient(RequestProficientDto requestProficientDto,
                                                                                   RequestSubServiceDto requestSubServiceDto) {
        Proficient proficient = modelMapper.map(requestProficientDto, Proficient.class);
        SubService subService = modelMapper.map(requestSubServiceDto, SubService.class);
        List<Orders> orders = proficientService.showOrdersSubServiceToProficient(proficient, subService);
        List<ResponseOrderDto> orderDto = new ArrayList<>();
        for (Orders order : orders) {
            orderDto.add(modelMapper.map(order, ResponseOrderDto.class));
        }
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @PostMapping("/showSubServices")
    public ResponseEntity<List<ResponseSubServiceDto>> showSubServiceToProficient(@RequestBody RequestProficientDto requestProficientDto) {
        Proficient proficient = modelMapper.map(requestProficientDto, Proficient.class);
        List<SubService> subServices = proficientService.showSubServiceToProficient(proficient);
        List<ResponseSubServiceDto> responseSubServiceDto = new ArrayList<>();
        for (SubService subService : subServices) {
            responseSubServiceDto.add(modelMapper.map(subService, ResponseSubServiceDto.class));
        }
        return new ResponseEntity<>(responseSubServiceDto, HttpStatus.OK);
    }

    @GetMapping("/{id}/showScore")
    public ResponseEntity<Integer> showScore(@PathVariable("id") long id) {
        int score = proficientService.showScoreToProficient(id);
        return new ResponseEntity<>(score, HttpStatus.OK);
    }


    @GetMapping("/{id}/getPicture")
    @ResponseBody
    public ResponseEntity<byte[]> getPicture(@PathVariable("id") Long id) {
        byte[] imageBytes = proficientService.getPicture(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}
