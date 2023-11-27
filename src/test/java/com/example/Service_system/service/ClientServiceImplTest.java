package com.example.Service_system.service;

import com.example.Service_system.dto.subServiceDto.entity.*;
import com.example.Service_system.entity.*;
import com.example.Service_system.enumration.OrderStatus;
import com.example.Service_system.enumration.ProficientStatus;
import com.example.Service_system.exception.CustomException;
import com.example.Service_system.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private SubServiceRepository subServiceRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ServiceRepository serviceRepository;
    @Mock
    private OffersRepository offersRepository;
    @Mock
    private OffersService offersService;
    @Mock
    private OrderService orderService;
    @Mock
    private CommentsRepository commentsRepository;
    @Mock
    private ProficientService proficientService;

    private Client client;
    private SubService subService;
    private Orders orders;
    private Services service;
    private Offers offers;
    private Proficient proficient;
    private Comments comments;


    @InjectMocks
    private ClientServiceImpl underTest;

    @BeforeEach
    void setUp() {
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
        offers = new Offers(
                1L,
                orders,
                proficient,
                LocalDateTime.now(),
                123,
                LocalDateTime.now().plusDays(1),
                Time.valueOf(LocalTime.of(1, 25))
        );
        comments = new Comments(
                1,
                "fjfdfk",
                proficient,
                orders
        );
    }

    @Test
    void save() {
        when(clientRepository.save(any())).thenReturn(client);
        underTest.save(client);
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void delete() throws CustomException {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        underTest.delete(client);
    }

    @Test
    void delete2() throws CustomException {
        assertThrows(CustomException.class, () -> underTest.delete(client));
    }

    @Test
    void update() throws CustomException {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        underTest.update(client);
    }

    @Test
    void update2() throws CustomException {
        assertThrows(CustomException.class, () -> underTest.update(client));
    }

    @Test
    void find() throws CustomException {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        underTest.find(1);
    }

    @Test
    void find2() {
        assertThrows(CustomException.class, () -> underTest.find(1));
    }

    @Test
    void findByEmailPassword() throws CustomException {
        when(clientRepository.findUsersByEmailAddressAndPassword("mobina@gmail.com", "mobina12345amn")).thenReturn(client);
        underTest.findByEmailPassword("mobina@gmail.com", "mobina12345amn");
    }

    @Test
    void findByEmailPassword2() {
        assertThrows(CustomException.class, () -> underTest.findByEmailPassword("mobina@gmail.com", "mobina12345amn"));
    }

    @Test
    void passwordUpdate() throws CustomException {
        when(clientRepository.findUsersByEmailAddressAndPassword("mobina@gmail.com", "mobina12345amn")).thenReturn(client);
        underTest.changePassword("mobina@gmail.com", "mobina12345amn", "fnmjmdcm1234");
    }

    @Test
    void passwordUpdate2() {
        assertThrows(CustomException.class, () -> underTest.changePassword("mobina@gmail.com", "mobina12345amn", "fnmjmdcm1234"));
    }

    @Test
    void addOrder() {
        assertThrows(NullPointerException.class, () -> underTest.addOrder(client, subService, orders, proficient));

    }

    @Test
    void addOrder2() {
        orders.setTimeForWork(LocalDateTime.now().minusDays(1));
        when(subServiceRepository.findById(1L)).thenReturn(Optional.of(subService));
        assertThrows(CustomException.class, () -> underTest.addOrder(client, subService, orders, proficient));

    }

    @Test
    void addOrder3() {
        orders.setTimeForWork(LocalDateTime.now().plusDays(1));
        orders.setProposedPrice(120);
        when(subServiceRepository.findById(1L)).thenReturn(Optional.of(subService));
        assertThrows(CustomException.class, () -> underTest.addOrder(client, subService, orders, proficient));

    }

    @Test
    void addOrder4() {
        orders.setAddress(null);
        orders.setDescription(null);
        when(subServiceRepository.findById(1L)).thenReturn(Optional.of(subService));
        assertThrows(CustomException.class, () -> underTest.addOrder(client, subService, orders, proficient));

    }

    @Test
    void addOrder5() throws CustomException {
        when(subServiceRepository.findById(1L)).thenReturn(Optional.of(subService));
        underTest.addOrder(client, subService, orders, proficient);

    }

    @Test
    void showServiceToClient() {
        List<Services> services = new ArrayList<>();
        services.add(service);
        when(serviceRepository.findAll()).thenReturn(services);
        underTest.showServiceToClient(client);
    }

    @Test
    void showOrdersToClient() {
        when(orderRepository.findAll()).thenReturn(List.of());
        assertThrows(CustomException.class, () -> underTest.showOrdersToClient(client));
    }

    @Test
    void showOrdersToClient2() throws CustomException {
        when(orderRepository.findAll()).thenReturn(List.of(orders));
        underTest.showOrdersToClient(client);
    }


    @Test
    void showOffersForOrdersToClientSortedByProposedPrice1() {
        orders.setOffers(offers);
        underTest.showOffersForOrdersToClientSortedByProposedPrice(client, orders);
    }

    @Test
    void showOffersForOrdersToClientSortedByScore() {
        orders.setOffers(offers);
        underTest.showOffersForOrdersToClientSortedByScore(client, orders);
    }

    @Test
    void chooseProficient() throws CustomException {
        underTest.chooseProficient(orders, proficient);
    }

    @Test
    void startWork() throws CustomException {
        Offers offers = new Offers(
                1L,
                orders,
                proficient,
                LocalDateTime.now().minusMinutes(2),
                123,
                LocalDateTime.now().minusDays(1),
                Time.valueOf(LocalTime.of(1, 25))
        );
        orders.setTimeForWork(LocalDateTime.now().plusDays(2));
        orders.setProficient(proficient);
        when(offersService.findOffersByOrdersAndProficient(orders, proficient)).thenReturn(offers);
        underTest.startWork(client, orders);
    }

    @Test
    void startWork2() throws CustomException {
        when(offersService.findOffersByOrdersAndProficient(orders, proficient)).thenReturn(offers);
        orders.setProficient(proficient);
        assertThrows(CustomException.class, () -> underTest.startWork(client, orders));
    }

    @Test
    void setComment() throws CustomException {
        when(orderRepository.findByUser(client)).thenReturn(orders);
        underTest.setComment(client, orders, comments);
    }

    @Test
    void setComment2() {
        when(orderRepository.findByUser(client)).thenReturn(null);
        assertThrows(NullPointerException.class, () -> underTest.setComment(client, orders, comments));
    }

    @Test
    void setComment3() throws CustomException {
        Orders order = new Orders(2L, 124, "absdf", LocalDateTime.now().plusDays(1),
                "hjdjukfv",
                OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_EXPERT,
                subService,
                client
        );
        when(orderRepository.findByUser(client)).thenReturn(order);
        assertThrows(CustomException.class, () -> underTest.setComment(client, orders, comments));
    }

    @Test
    void stopWork() throws CustomException {
        orders.setTimeForWork(LocalDateTime.now().plusDays(2));
        orders.setProficient(proficient);
        orders.setOrderStatus(OrderStatus.STARTED);
        underTest.stopWork(client, orders);
    }


    @Test
    void stopWork2() {
    orders.setOrderStatus(OrderStatus.WAITING_FOR_PROFICIENT_SELECTION);
        assertThrows(CustomException.class, () -> underTest.stopWork(client, orders));
    }
}