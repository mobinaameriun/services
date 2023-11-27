package com.example.Service_system.service;

import com.example.Service_system.dto.subServiceDto.entity.*;
import com.example.Service_system.entity.*;
import com.example.Service_system.enumration.OrderStatus;
import com.example.Service_system.enumration.ProficientStatus;
import com.example.Service_system.exception.CustomException;
import com.example.Service_system.repository.OrderRepository;
import com.example.Service_system.repository.ProficientRepository;
import com.example.Service_system.repository.SubServiceRepository;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProficientServiceTest {

    @Mock
    private ProficientRepository proficientRepository;
    @Mock
    private SubServiceRepository subServiceRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OffersService offersService;
    @Mock
    private OrderService orderService;


    @InjectMocks
    private ProficientService underTest;

    private Proficient proficient;
    private Orders orders;
    private SubService subService;
    private Services service;
    private Client client;
    private Comments comments;


    @Rule
    public TemporaryFolder folder = new TemporaryFolder();


    @BeforeEach
    void setProficient() throws IOException {
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
        orders = new Orders(1L, 123, "absdf", LocalDateTime.now(),
                "hjdjukfv",
                OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_EXPERT,
                subService,
                client
        );
        comments = new Comments(
                1,
                "fjfdfk",
                proficient,
                orders
        );


        folder.create();
    }

    @Test
    void save() {
        underTest.save(proficient);
    }

    @Test
    void delete() {
        underTest.delete(proficient);
    }

    @Test
    void update() {
        assertThrows(CustomException.class, () -> underTest.update(proficient));
    }

    @Test
    void update1() throws CustomException {
        when(proficientRepository.findById(1L)).thenReturn(Optional.of(proficient));
        underTest.update(proficient);
    }

    @Test
    void find() {
        assertThrows(CustomException.class, () -> underTest.find(1));
    }

    @Test
    void find2() throws CustomException {
        when(proficientRepository.findById(1L)).thenReturn(Optional.of(proficient));
        underTest.find(1);
    }

    @Test
    void signupProficients() throws IOException, CustomException {
        File file = folder.newFile("picture1.jpg");
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.setLength(300 * 1024);
        raf.close();
        underTest.signupProficients(proficient,file);

    }

    @Test
    void signupProficients2() throws IOException {
        File file = folder.newFile("picture1.jpg");
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.setLength(400 * 1024);
        raf.close();
        assertThrows(CustomException.class,() -> underTest.signupProficients(proficient,file));

    }

    @Test
    void signupProficients3() {
        File file = new File("picture");
        assertThrows(IOException.class,() -> underTest.signupProficients(proficient,file));

    }


    @Test
    void pictureValidation() throws IOException {
        File file = folder.newFile("picture1.jpg");
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.setLength(300 * 1024);
        raf.close();
        boolean b = underTest.pictureValidation(file);
        assertTrue(b);
    }

    @Test
    void pictureValidation2() throws IOException {
        File file = folder.newFile("picture2.txt");
        RandomAccessFile raf2 = new RandomAccessFile(file, "rw");
        raf2.setLength(300 * 1024);
        raf2.close();
        boolean b = underTest.pictureValidation(file);
        assertFalse(b);
    }

    @Test
    void pictureValidation3() throws IOException {
        File file = folder.newFile("picture3.jpg");
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.setLength(400 * 1024);
        raf.close();
        boolean b = underTest.pictureValidation(file);
        assertFalse(b);
    }

    @Test
    void recordOffers() throws CustomException {
        proficient.setStatus(ProficientStatus.NOT_CONFIRMED);
        assertThrows(CustomException.class,() -> underTest.recordOffers(proficient, orders, 123, LocalDateTime.now().plusDays(1), Time.valueOf(LocalTime.of(2, 30))));
    }

    @Test
    void recordOffers2() throws CustomException {
        orders.setOrderStatus(OrderStatus.STARTED);
        assertThrows(CustomException.class,() -> underTest.recordOffers(proficient, orders, 123, LocalDateTime.now().plusDays(1), Time.valueOf(LocalTime.of(2, 30))));
    }

    @Test
    void recordOffers3() throws CustomException {
        assertThrows(CustomException.class,() -> underTest.recordOffers(proficient, orders, 123, LocalDateTime.now().plusDays(1), Time.valueOf(LocalTime.of(2, 30))));
    }

    @Test
    void recordOffers4() throws CustomException {
        proficient.setSubServiceList(subService);
        assertThrows(CustomException.class,() -> underTest.recordOffers(proficient, orders, 120, LocalDateTime.now().plusDays(1), Time.valueOf(LocalTime.of(2, 30))));
    }

    @Test
    void recordOffers5() throws CustomException {
        proficient.setSubServiceList(subService);
        assertThrows(CustomException.class,() -> underTest.recordOffers(proficient, orders, 123, LocalDateTime.now().minusDays(1), Time.valueOf(LocalTime.of(2, 30))));
    }

    @Test
    void recordOffers6() throws CustomException {
        proficient.setSubServiceList(subService);
        underTest.recordOffers(proficient,orders,123,LocalDateTime.now().plusDays(1),Time.valueOf(LocalTime.of(2, 30)));
    }

    @Test
    void showSubServiceToProficient() throws CustomException {
        proficient.setSubServiceList(subService);
        underTest.showSubServiceToProficient(proficient);
    }

    @Test
    void showSubServiceToProficient2() throws CustomException {
        assertThrows(CustomException.class,() -> underTest.showSubServiceToProficient(proficient));
    }

    @Test
    void showSubServiceToProficient3() throws CustomException {
        proficient.setStatus(ProficientStatus.NOT_CONFIRMED);
        assertThrows(CustomException.class,() -> underTest.showSubServiceToProficient(proficient));
    }

    @Test
    void updateProficientScore() throws CustomException {
        proficient.setComments(comments);
        when(proficientRepository.findById(1L)).thenReturn(Optional.of(proficient));
        underTest.updateProficientScore(proficient);
    }
    @Test
    void getPicture() throws CustomException, IOException {
        File file=folder.newFile("picture.jpg");
        proficient.setPicture(file.getAbsolutePath());
        when(proficientRepository.findById(1L)).thenReturn(Optional.of(proficient));
        underTest.getPicture(proficient);
    }

    @Test
    void getPicture2() {
        when(proficientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CustomException.class,() -> underTest.getPicture(proficient));
    }

    @Test
    void showOrdersSubServiceToProficient(){
        proficient.setStatus(ProficientStatus.NOT_CONFIRMED);
        assertThrows(CustomException.class,()->underTest.showOrdersSubServiceToProficient(proficient,subService));

    }

    @Test
    void showOrdersSubServiceToProficient2(){
        proficient.setStatus(ProficientStatus.ACCEPTED);
        assertThrows(CustomException.class,()->underTest.showOrdersSubServiceToProficient(proficient,subService));

    }

    @Test
    void showOrdersSubServiceToProficient3() throws CustomException {
        proficient.setStatus(ProficientStatus.ACCEPTED);
        proficient.setSubServiceList(subService);
        underTest.showOrdersSubServiceToProficient(proficient,subService);

    }
}