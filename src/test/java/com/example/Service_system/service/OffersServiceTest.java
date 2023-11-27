package com.example.Service_system.service;

import com.example.Service_system.dto.subServiceDto.entity.*;
import com.example.Service_system.entity.*;
import com.example.Service_system.enumration.OrderStatus;
import com.example.Service_system.enumration.ProficientStatus;
import com.example.Service_system.exception.CustomException;
import com.example.Service_system.repository.OffersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OffersServiceTest {

    private Offers offers;
    private Orders orders;
    private Proficient proficient;
    private Client client;
    private SubService subService;
    private Services service;

    @Mock
    private OffersRepository offersRepository;


    @InjectMocks
    private OffersService underTest;

    @BeforeEach
    void setUp(){
        offers = new Offers(
                1L,
                orders,
                proficient,
                LocalDateTime.now(),
                123,
                LocalDateTime.now().plusDays(1),
                Time.valueOf(LocalTime.of(1,25))
        );
        orders = new Orders(1L, 123, "absdf", LocalDateTime.now().plusDays(1),
                "hjdjukfv",
                OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_EXPERT,
                subService,
                client
        );
        proficient = new Proficient(1L,
                "mobina",
                "amerion",
                "mobina@gmail.com",
                "mobina12345asd",
                LocalDateTime.now(),
                ProficientStatus.ACCEPTED);
        client = new Client(
                1L,
                "mobina",
                "asf",
                "mobina@gmail.com",
                "mobina12345amn",
                LocalDateTime.now()
        );
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
    }

    @Test
    void save() {
        underTest.save(offers);
    }

    @Test
    void delete() {
        assertThrows(CustomException.class,() -> underTest.delete(offers));
    }

    @Test
    void delete2() throws CustomException {
        when(offersRepository.findById(1L)).thenReturn(Optional.of(offers));
        underTest.delete(offers);
    }

    @Test
    void findById() {
        assertThrows(CustomException.class,() -> underTest.findById(1L));
    }

    @Test
    void findById2() throws CustomException {
        when(offersRepository.findById(1L)).thenReturn(Optional.of(offers));
        underTest.findById(1L);
    }

    @Test
    void update() throws CustomException {
        when(offersRepository.findById(1L)).thenReturn(Optional.of(offers));
        underTest.update(offers);
    }

    @Test
    void update2() {
        assertThrows(CustomException.class,() -> underTest.update(offers));
    }

    @Test
    void findOffersByOrders() {
        when(offersRepository.findOffersByOrders(orders)).thenReturn(Optional.of(offers));
        underTest.findOffersByOrders(orders);
    }

    @Test
    void findOffersByOrdersAndProficient() {
        when(offersRepository.findOffersByOrdersAndProficient(orders,proficient)).thenReturn(offers);
        underTest.findOffersByOrdersAndProficient(orders,proficient);
    }
}