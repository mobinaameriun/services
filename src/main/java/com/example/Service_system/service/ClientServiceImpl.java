package com.example.Service_system.service;

import com.example.Service_system.entity.*;
import com.example.Service_system.enumration.OrderStatus;
import com.example.Service_system.exception.CustomException;
import com.example.Service_system.exception.DuplicateException;
import com.example.Service_system.exception.NotFoundException;
import com.example.Service_system.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl {

    private final ClientRepository clientRepository;
    private final ServiceService serviceService;
    private final OrderService orderService;
    private final OffersService offersService;
    private final ProficientService proficientService;
    private final SubServiceService subServiceService;
    private final CommentService commentService;
//    private final ManagerService managerService;


    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ServiceService serviceService, OrderService orderService,
                             OffersService offersService, ProficientService proficientService, SubServiceService subServiceService, CommentService commentService) {
        this.clientRepository = clientRepository;
        this.serviceService = serviceService;
        this.orderService = orderService;
        this.offersService = offersService;
        this.proficientService = proficientService;
        this.subServiceService = subServiceService;
        this.commentService = commentService;
//        this.managerService = managerService;
    }

    public Client save(Client client) {
        try {
            return clientRepository.save(client);
        } catch (Exception e) {
            throw new DuplicateException(String.format("%s is duplicate", client.getEmailAddress()));
        }
    }

    public void deleteClient(Manager tempManager, long clientId) {
        try {
            find(clientId);
            deleteById(clientId);
        } catch (Exception e) {
            throw new NotFoundException("client not found");
        }
    }

    public void deleteById(long clientId) {
        try {
            clientRepository.deleteById(clientId);
        } catch (Exception e) {
            throw new NotFoundException("client not found");
        }
    }

    public void delete(Client client) {
        try {
            clientRepository.delete(client);
        } catch (Exception e) {
            throw new NotFoundException(String.format("%s is not found", client));
        }

    }

    public void update(Client client) {
        if (clientRepository.findById(client.getId()).isPresent()) {
            clientRepository.save(client);
        } else
            throw new CustomException("this client not found");
    }

    public Client find(long id) {
        Client client;
        try {
            client = clientRepository.findById(id).get();
        } catch (Exception e) {
            throw new NotFoundException("this client not found");
        }
        return client;
    }

    public Client findByEmailPassword(String emailAddress, String password) {
        Client result;
        try {
            result = clientRepository.findUsersByEmailAddressAndPassword(emailAddress, password);
        } catch (Exception e) {
            throw new NotFoundException("this client not found");
        }
        return result;
    }

    public void changePassword(String emailAddress, String oldPassword, String newPassword) throws CustomException {
        Client resultClient;
        try {
            resultClient = clientRepository.findUsersByEmailAddressAndPassword(emailAddress, oldPassword);
            resultClient.setPassword(newPassword);
            clientRepository.save(resultClient);
        } catch (Exception e) {
            throw new CustomException("this user not found ");
        }
    }

    public void addOrder(Client client, SubService subService, Orders order) {
        try {
            //     Optional<Proficient> proficient1 = proficientService.find(proficient.getId());
            //     if (proficient1.isPresent()) {
            //        if (proficient1.get().getStatus().equals(ProficientStatus.ACCEPTED)) {
            Optional<SubService> subService1 = subServiceService.find(subService.getId());
            if (order.getTimeForWork().isAfter(LocalDateTime.now())) {
                if (order.getProposedPrice() >= subService.getBasePrice()) {
                    if (order.getDescription() != null && order.getAddress() != null) {
                        //                        order.setProficient(proficient1.get());
                        order.setClient(client);
                        order.setSubService(subService1.get());
                        order.setOrderStatus(OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_EXPERT);
                        orderService.save(order);
                    } else
                        throw new CustomException("Incomplete information");
                } else
                    throw new CustomException("this price invalid");
            } else
                throw new CustomException("invalid date ");
            //        } else {
            //            throw new CustomException("this proficient not accepted");
            //         }
            //    } else {
            //         throw new CustomException("proficient not found");
            //    }
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    public List<Services> showServiceToClient(Client client) {
        find(client.getId());
        return serviceService.findAll();
    }

    public List<Orders> showOrdersToClient(Client client) {
        Client tempClient;
        try {
            tempClient = find(client.getId());
        } catch (Exception e) {
            throw new CustomException("Client not found");
        }
        try {
            return orderService.findByClient(tempClient);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

    }

    public List<Offers> showOffersForOrdersToClientSortedByProposedPrice(Client client, Orders orders) {
        Client client1 = find(client.getId());
        Optional<Orders> orders1 = orderService.find(orders.getId());
        if (orderService.findByClient(client1).contains(orders1.get())) {
            return offersService.findOffersByOrders(orders1.get()).stream().
                    sorted(Comparator.comparing(Offers::getProposedPrice)).toList();
        } else
            throw new CustomException("this order dont for this client");
    }

    public List<Offers> showOffersForOrdersToClientSortedByScore(Client client, Orders orders) throws CustomException {
        Client client1 = find(client.getId());
        Optional<Orders> orders1 = orderService.find(orders.getId());
        if (orderService.findByClient(client1).contains(orders1.get())) {
            return offersService.findOffersByOrders(orders1.get()).stream().
                    sorted(Comparator.comparing(Offers -> Offers.getProficient().getScore())).toList();
        } else throw new CustomException("this order dont for this client");

    }

    @Transactional
    public void chooseProficient(Orders orders, Proficient proficient) {
        Orders orders1 = orderService.find(orders.getId()).get();
        Proficient proficient1 = proficientService.find(proficient.getId()).get();
        Offers offer = offersService.findOffersByOrdersAndProficient(orders, proficient);
        orders1.setProficient(proficient);
        orders1.setOrderStatus(OrderStatus.WAITING_FOR_THE_PROFICIENT_TO_COME_TO_YOUR_PLACE);
        orderService.update(orders1);

    }

    @Transactional
    public void startWork(Client client, Orders orders) {
        Client client1 = find(client.getId());
        Orders orders1 = orderService.find(orders.getId()).get();
        if (orderService.findByClient(client1).contains(orders1)) {
            Offers offers = offersService.findOffersByOrdersAndProficient(orders1, orders1.getProficient());
            Offers offers1 = offers;
            if (LocalDateTime.now().isAfter(offers1.getTimeForStartWork())) {
                orders1.setOrderStatus(OrderStatus.STARTED);
                orderService.update(orders1);
            } else
                throw new CustomException("error1");
        } else
            throw new CustomException("error2");
    }

    @Transactional
    public void setComment(Client client, Orders orders, Comments comments) {
        Client client1 = find(client.getId());
        Orders orders1 = orderService.find(orders.getId()).get();
        List<Orders> byUser = orderService.findByClient(client1);
        if (byUser.contains(orders1)) {
            Proficient proficient = orders1.getProficient();
            comments.setOrders(orders1);
            comments.setProficient(proficient);
            commentService.save(comments);
            proficientService.updateProficientScore(proficient);
        } else {
            throw new CustomException("error");
        }
    }

    public void stopWork(Client client, Orders orders) throws CustomException {
        client = find(client.getId());
        orders = orderService.find(orders.getId()).get();
        if (orderService.findByClient(client).contains(orders)) {
            if (orders.getOrderStatus().equals(OrderStatus.STARTED)) {
                orders.setOrderStatus(OrderStatus.COMPLETED);
                orderService.update(orders);
                Proficient proficient = orders.getProficient();
                Offers offers = offersService.findOffersByOrdersAndProficient(orders, proficient);
                if (offers.getTime().getHours() < (Duration.between(LocalDateTime.now(), offers.getTimeForStartWork())).toHours()) {
                    proficient.setMinusScore(proficient.getMinusScore() + 1);
                    proficientService.updateProficientScore(proficient);
                }

            } else
                throw new CustomException("error");
        } else
            throw new CustomException("this information invalid");
    }

    public void paymentOfValidity(Client client, Orders orders) {
        client = find(client.getId());
         orders = orderService.find(orders.getId()).get();
        if (client.equals(orders.getClient())) {
            Offers offers = offersService.findOffersByOrdersAndProficient(orders, orders.getProficient());
            if (client.getValidity() >= offers.getProposedPrice()) {
                client.setValidity(client.getValidity() - offers.getProposedPrice());
                update(client);
                offers.getProficient().setValidity(offers.getProposedPrice() * 70 / 100);
                proficientService.update(offers.getProficient());
            } else
                throw new CustomException("your validity is not enough");
        }
    }


}

