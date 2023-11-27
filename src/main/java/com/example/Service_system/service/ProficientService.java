package com.example.Service_system.service;

import com.example.Service_system.entity.*;
import com.example.Service_system.enumration.OrderStatus;
import com.example.Service_system.enumration.ProficientStatus;
import com.example.Service_system.exception.CustomException;
import com.example.Service_system.exception.NotFoundException;
import com.example.Service_system.repository.ProficientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProficientService {
    private final ProficientRepository proficientRepository;
    private final OrderService orderService;
    private final OffersService offersService;

    @Autowired
    public ProficientService(ProficientRepository proficientRepository, OrderService orderService, OffersService offersService) {
        this.proficientRepository = proficientRepository;
        this.orderService = orderService;
        this.offersService = offersService;
    }


    public Proficient save(Proficient proficient) {
        return proficientRepository.save(proficient);
    }

    public void deleteProficient(Manager tempManager, long proficientId) {
        try {
            find(proficientId);
            deleteById(proficientId);
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    public void deleteById(long proficientId) {
        proficientRepository.deleteById(proficientId);
    }

    public void delete(Proficient proficient) {
        proficientRepository.delete(proficient);
    }


    public void update(Proficient proficient) {
        Optional<Proficient> byId = proficientRepository.findById(proficient.getId());
        if (byId.isPresent()) {
            proficientRepository.save(proficient);
        } else
            throw new CustomException("this proficient not found");
    }

    public Optional<Proficient> find(long id) throws CustomException {
        Optional<Proficient> byId = proficientRepository.findById(id);
        if (byId.isPresent()) {
            return byId;
        } else
            throw new CustomException("this proficient not found");
    }

    public Proficient signupProficients(Proficient proficient, MultipartFile picture) {
        Proficient proficient1;
        if (pictureValidation(picture)) {
            try {
                proficient.setPicture(picture.getBytes());
                proficient.setStatus(ProficientStatus.NOT_CONFIRMED);
                proficient.setScore(0);
                proficient1 = save(proficient);
            } catch (Exception e) {
                throw new CustomException(e.getMessage());
            }
        } else
            throw new CustomException("this file is invalid");
        return proficient1;
    }

    public boolean pictureValidation(MultipartFile picture) {
        if (picture.getSize() <= 300 * 1024 && picture.getContentType().equals("image/jpeg")) {
            return true;
        }
        return false;
    }

    public void recordOffers(Proficient proficient, Orders orders, Offers offer) {
        Optional<Proficient> tempProficient = proficientRepository.findById(proficient.getId());
        if (tempProficient.isPresent()) {
            proficient = tempProficient.get();
            if (proficient.getStatus().equals(ProficientStatus.ACCEPTED)) {
                Optional<Orders> tempOrder = orderService.find(orders.getId());
                if (tempOrder.isPresent()) {
                    orders = tempOrder.get();
                    Offers tempOffer = offersService.findOfferByOrdersAndProficient(orders.getId(), proficient.getId());
                    if (tempOffer == null) {
                        if (orders.getOrderStatus().equals(OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_EXPERT) ||
                                orders.getOrderStatus().equals(OrderStatus.WAITING_FOR_PROFICIENT_SELECTION)) {
                            List<SubService> subServiceList = proficient.getSubServiceList();
                            if (subServiceList.contains(orders.getSubService())) {
                                if (offer.getProposedPrice() >= orders.getSubService().getBasePrice()) {
                                    if (offer.getTimeForStartWork().isAfter(LocalDateTime.now())) {
                                        orders.setOrderStatus(OrderStatus.WAITING_FOR_PROFICIENT_SELECTION);
                                        orderService.update(orders);
                                        offer.setOrders(orders);
                                        offer.setProficient(proficient);
                                        offer.setDateOfRecord(LocalDateTime.now());
                                        offersService.save(offer);
                                    } else
                                        throw new CustomException("invalid date");
                                } else
                                    throw new CustomException("invalid price");
                            } else
                                throw new CustomException("this sub service is not found");
                        } else
                            throw new CustomException("this order status invalid for suggestion");
                    } else {
                        throw new CustomException("You submited your offer");
                    }
                } else {
                    throw new CustomException("Order not found");
                }
            } else
                throw new CustomException("First, you must confirm this person's access to the system and then add her to the sub-services");
        } else {
            throw new CustomException("Proficient not found");
        }
    }

    public List<SubService> showSubServiceToProficient(Proficient proficient) {
        Optional<Proficient> tempProficient = proficientRepository.findById(proficient.getId());
        if (tempProficient.isPresent()) {
            proficient = tempProficient.get();
            if (proficient.getStatus().equals(ProficientStatus.ACCEPTED)) {
                List<SubService> subServiceList = proficient.getSubServiceList();
                if (subServiceList.isEmpty())
                    throw new CustomException("this sub service is not found");
                else
                    return subServiceList;
            } else
                throw new CustomException("First, you must confirm this person's access to the system and then add her to the sub-services");
        } else {
            throw new CustomException("Proficient not found");
        }
    }

    public void updateProficientScore(Proficient proficient) {
        int resultScore = 0;
        Proficient proficient1 = proficientRepository.findById(proficient.getId()).get();
        List<Integer> integers = proficient1.getComments().stream().map(Comments::getScore).toList();
        for (int score : integers) {
            resultScore += score;
        }
        proficient1.setScore((resultScore / integers.size()) - proficient.getMinusScore());
        if (proficient1.getScore() < 0) {
            proficient1.setStatus(ProficientStatus.UNAVAILABLE);
        }

        update(proficient1);
    }

    public byte[] getPicture(long proficientId) {
        Optional<Proficient> byId = proficientRepository.findById(proficientId);
        if (byId.isPresent()) {
            return byId.get().getPicture();
        } else
            throw new CustomException("error");
    }

    public List<Orders> showOrdersSubServiceToProficient(Proficient proficient, SubService subService) {
        Optional<Proficient> tempProficient = proficientRepository.findById(proficient.getId());
        if (tempProficient.isPresent()) {
            proficient = tempProficient.get();
            if (proficient.getStatus().equals(ProficientStatus.ACCEPTED)) {
                List<SubService> subServiceList = proficient.getSubServiceList();
                if (subServiceList.isEmpty())
                    throw new CustomException("this sub service is not found");
                else {
                    return orderService.findOpenOrderBySubService(subService);
                }
            } else
                throw new CustomException("First, you must confirm this person's access to the system and then add her to the sub-services");
        } else {
            throw new CustomException("Proficient not found");
        }
    }

    public List<Proficient> loadAll() {
        return proficientRepository.findAll();
    }

    public int showScoreToProficient(long id) {
        Optional<Proficient> proficient = proficientRepository.findById(id);
        if (proficient.isPresent()) {
            return proficient.get().getScore();
        } else {
            throw new CustomException("Proficient not found");
        }
    }

}
