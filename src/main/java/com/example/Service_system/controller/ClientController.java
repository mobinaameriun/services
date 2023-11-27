package com.example.Service_system.controller;

import com.example.Service_system.dto.client.*;
import com.example.Service_system.dto.comment.RequestCommentDto;
import com.example.Service_system.dto.manager.RequestManagerDto;
import com.example.Service_system.dto.offer.ResponseOfferDto;
import com.example.Service_system.dto.order.RequestOrderDto;
import com.example.Service_system.dto.payment.PaymentDto;
import com.example.Service_system.dto.proficient.RequestProficientDto;
import com.example.Service_system.dto.subService.RequestSubServiceDto;
import com.example.Service_system.entity.*;
import com.example.Service_system.model.response.ResponseModel;
import com.example.Service_system.service.ClientServiceImpl;
import com.example.Service_system.service.ManagerService;
import jakarta.validation.Valid;
import org.dozer.DozerBeanMapper;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientServiceImpl clientService;
    private final ModelMapper modelMapper;
    private final DozerBeanMapper dozerBeanMapper;

    @Autowired
    public ClientController(ClientServiceImpl clientService, ModelMapper modelMapper, DozerBeanMapper dozerBeanMapper) {
        this.clientService = clientService;
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
        modelMapper.addConverter(converter);
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseClientDto> saveClient(@Valid @RequestBody RequestClientForSignupDto requestClient) {
        Client client = modelMapper.map(requestClient, Client.class);
        clientService.save(client);
        ResponseClientDto responseClient = modelMapper.map(client, ResponseClientDto.class);
        return new ResponseEntity<>(responseClient, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ResponseClientDto> deleteUser(@PathVariable("id") long id, @RequestBody RequestManagerDto requestManagerDto) {
        Manager manager = modelMapper.map(requestManagerDto, Manager.class);
        clientService.deleteClient(manager, id);
        ResponseClientDto responseClientDto = modelMapper.map(manager, ResponseClientDto.class);
        return new ResponseEntity<>(responseClientDto, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseClientDto> updateClint(@RequestBody RequestUpdateClientDto requestUpdateClientDto) {
        Client client = modelMapper.map(requestUpdateClientDto, Client.class);
        clientService.update(client);
        ResponseClientDto responseClientDto = modelMapper.map(client, ResponseClientDto.class);
        return new ResponseEntity<>(responseClientDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseClientDto> findClient(@PathVariable Long id) {
        Client client = clientService.find(id);
        ResponseClientDto responseClientDto = modelMapper.map(client, ResponseClientDto.class);
        return new ResponseEntity<>(responseClientDto, HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<ResponseModel> changePassword(@RequestBody ClientModelForChangePassword changePassword) {
        clientService.changePassword(changePassword.getEmailAddress()
                , changePassword.getOldPassword()
                , changePassword.getNewPassword());
        return new ResponseEntity<>(new ResponseModel("password updated"), HttpStatus.OK);
    }

    @RequestMapping(path = "/addOrder", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.ALL_VALUE})
    public ResponseEntity<ResponseModel> addOrder(
            RequestClientDto requestClientDto,
            RequestSubServiceDto requestSubServiceDto,
            RequestOrderDto requestOrderDto
    ) {
        Client client = modelMapper.map(requestClientDto, Client.class);
        SubService subService = modelMapper.map(requestSubServiceDto, SubService.class);
        Orders orders = modelMapper.map(requestOrderDto, Orders.class);
        clientService.addOrder(client, subService, orders);
        return new ResponseEntity<>(new ResponseModel("this order recorded"), HttpStatus.OK);
    }

    @PostMapping("/showServiceToClient")
    public ResponseEntity<List<Services>> showServiceToClient(@RequestBody RequestClientDto requestClientDto) {
        Client client = modelMapper.map(requestClientDto, Client.class);
        List<Services> services = clientService.showServiceToClient(client);
        return new ResponseEntity<>(services, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/showOrdersToClient")
    public ResponseEntity<List<Orders>> showOrdersToClient(@RequestBody RequestClientDto requestClientDto) {
        Client client = modelMapper.map(requestClientDto, Client.class);
        List<Orders> orders = clientService.showOrdersToClient(client);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @RequestMapping(path = "/showOffersForOrdersToClientSortedByProposedPrice", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.ALL_VALUE})
    public ResponseEntity<List<Offers>> showOffersForOrdersToClientSortedByProposedPrice(
             RequestClientDto requestClientDto,
             RequestOrderDto requestOrderDto) {
        Client client = modelMapper.map(requestClientDto, Client.class);
        Orders orders = modelMapper.map(requestOrderDto, Orders.class);
        List<Offers> offers = clientService.showOffersForOrdersToClientSortedByProposedPrice(client, orders);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @RequestMapping(path = "/showOffersForOrdersToClientSortedByScore", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.ALL_VALUE})
    public ResponseEntity<List<ResponseOfferDto>> showOffersForOrdersToClientSortedByScore(
             RequestClientDto requestClientDto,
             RequestOrderDto requestOrderDto) {
        Client client = modelMapper.map(requestClientDto, Client.class);
        Orders orders = modelMapper.map(requestOrderDto, Orders.class);
        List<Offers> offers = clientService.showOffersForOrdersToClientSortedByScore(client, orders);
        List<ResponseOfferDto> offerDtos = new ArrayList<>();
        for (Offers offer : offers) {
            offerDtos.add(modelMapper.map(offer, ResponseOfferDto.class));
        }
        return new ResponseEntity<>(offerDtos, HttpStatus.OK);
    }

    @RequestMapping(path = "/chooseProficient", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.ALL_VALUE})
    public ResponseEntity<ResponseModel> chooseProficient(RequestOrderDto requestOrderDto, RequestProficientDto requestProficientDto) {
        Orders orders = modelMapper.map(requestOrderDto, Orders.class);
        Proficient proficient = modelMapper.map(requestProficientDto, Proficient.class);
        clientService.chooseProficient(orders, proficient);

        return new ResponseEntity<>(new ResponseModel("this proficient set for this order"), HttpStatus.OK);
    }

    @RequestMapping(path = "/startWork", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.ALL_VALUE})
    public ResponseEntity<ResponseModel> startWork( RequestClientDto requestClientDto,
                                                    RequestOrderDto requestOrderDto) {
        Client client = modelMapper.map(requestClientDto, Client.class);
        Orders orders = modelMapper.map(requestOrderDto, Orders.class);
        clientService.startWork(client, orders);
        return new ResponseEntity<>(new ResponseModel("this order started"), HttpStatus.OK);
    }

    @RequestMapping(path = "/setComment", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.ALL_VALUE})
    public ResponseEntity setComment( RequestClientDto requestClientDto,
                                      RequestOrderDto requestOrderDto,
                                      RequestCommentDto requestCommentDto) {
        Client client = modelMapper.map(requestClientDto, Client.class);
        Orders orders = modelMapper.map(requestOrderDto, Orders.class);
        Comments comments = modelMapper.map(requestCommentDto, Comments.class);
        clientService.setComment(client, orders, comments);
        return new ResponseEntity<>(new ResponseModel("this comment recorded"), HttpStatus.OK);
    }

    @RequestMapping(path = "/stopWork", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.ALL_VALUE})
    public ResponseEntity<ResponseModel> stopWork( RequestClientDto requestClientDto,
                                                   RequestOrderDto requestOrderDto) {
        Client client = modelMapper.map(requestClientDto, Client.class);
        Orders orders = modelMapper.map(requestOrderDto, Orders.class);
        clientService.stopWork(client, orders);
        return new ResponseEntity(new ResponseModel("stop work"), HttpStatus.OK);
    }


    @RequestMapping(path = "/paymentOfValidity", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.ALL_VALUE})
    public ResponseEntity<HttpStatus> paymentOfValidity( RequestClientDto requestClientDto,
                                                         RequestOrderDto requestOrderDto) {
        Client client = modelMapper.map(requestClientDto, Client.class);
        Orders orders = modelMapper.map(requestOrderDto, Orders.class);
        clientService.paymentOfValidity(client, orders);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/pay")
    @CrossOrigin("*")
    public ResponseEntity<HttpStatus> pay(@RequestBody PaymentDto paymentDto) {
        System.out.println(paymentDto);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
