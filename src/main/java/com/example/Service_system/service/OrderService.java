package com.example.Service_system.service;


import com.example.Service_system.entity.Client;
import com.example.Service_system.entity.Orders;
import com.example.Service_system.entity.SubService;
import com.example.Service_system.exception.CustomException;
import com.example.Service_system.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OrderService  {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public void save(Orders orders) {
        orderRepository.save(orders);
    }


    public void delete(Orders orders) throws CustomException {
        if (orderRepository.findById(orders.getId()).isPresent()) {
            orderRepository.delete(orders);
        }else
            throw new CustomException("error");
    }


    public void update(Orders orders) throws CustomException {
        Optional<Orders> byId = orderRepository.findById(orders.getId());
        if (byId.isPresent()){
            orderRepository.save(orders);
        }else
            throw new CustomException("this order is not found!");
    }


    public Optional<Orders> find(long id) throws CustomException {
        Optional<Orders> orders = orderRepository.findById(id);
        if (orders.isPresent()) {
            return orders;
        }else
            throw new CustomException("error");
    }

    public List<Orders> findByClient(Client client) throws CustomException {
        List<Orders> byClient = orderRepository.findByClient(client);
        if(byClient!=null)
            return byClient;
        throw new CustomException("this client dont have any orders");
    }
    public List<Orders> findAll(){
        return orderRepository.findAll();
    }

    public List<Orders> findBySubService(SubService subService) throws CustomException {
        List<Orders> bySubService = orderRepository.findBySubService(subService);
        if (bySubService.isEmpty())
            throw new CustomException("this order not found");
        return bySubService;
    }

    public List<Orders> findOpenOrderBySubService(SubService subService) throws CustomException {
        return orderRepository.findOpenOrdersBySubService(subService.getId());
    }
}
