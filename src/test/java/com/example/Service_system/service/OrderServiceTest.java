package com.example.Service_system.service;

import com.example.Service_system.entity.Client;
import com.example.Service_system.entity.Orders;
import com.example.Service_system.entity.Services;
import com.example.Service_system.entity.SubService;
import com.example.Service_system.enumration.OrderStatus;
import com.example.Service_system.exception.CustomException;
import com.example.Service_system.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    private Orders orders;
    private Services service;
    private SubService subService;
    private Client client;

    @Mock
    private OrderRepository orderRepository;


    @InjectMocks
    private OrderService underTest;

    @BeforeEach
    void setUp(){
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
        orders = new Orders(1L, 123, "absdf", LocalDateTime.now(),
                "hjdjukfv",
                OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_EXPERT,
                subService,
                client
        );
        client = new Client(
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
        underTest.save(orders);
    }

    @Test
    void delete() throws CustomException {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orders));
        underTest.delete(orders);
    }

    @Test
    void delete2() {
        assertThrows(CustomException.class,() -> underTest.delete(orders));
    }


    @Test
    void update() throws CustomException {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orders));
        underTest.update(orders);
    }

    @Test
    void update2() throws CustomException {
        assertThrows(CustomException.class,() -> underTest.update(orders));
    }

    @Test
    void find() throws CustomException {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orders));
        underTest.find(1);
    }

    @Test
    void find2() throws CustomException {
        assertThrows(CustomException.class,() -> underTest.find(1));
    }
}