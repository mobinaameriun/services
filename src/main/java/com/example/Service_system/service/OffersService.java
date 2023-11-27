package com.example.Service_system.service;

import com.example.Service_system.entity.Offers;
import com.example.Service_system.entity.Orders;
import com.example.Service_system.entity.Proficient;
import com.example.Service_system.exception.CustomException;
import com.example.Service_system.exception.NotFoundException;
import com.example.Service_system.repository.OffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OffersService {

    private final OffersRepository offersRepository;

    @Autowired
    public OffersService(OffersRepository offersRepository) {
        this.offersRepository = offersRepository;
    }

    public void save(Offers offers) {
        offersRepository.save(offers);
    }

    public void delete(Offers offers) throws CustomException {
        if (offersRepository.findById(offers.getId()).isPresent()) {
            offersRepository.delete(offers);
        }else
            throw new CustomException("error");
    }

    public Offers findById(Long id) throws CustomException {
        Optional<Offers> byId = offersRepository.findById(id);
        if (byId.isPresent())
            return byId.get();
        else
            throw new CustomException("the offer not found");
    }

    public void update(Offers offers) throws CustomException {
        if (offersRepository.findById(offers.getId()).isPresent()) {
            offersRepository.save(offers);
        }else
            throw new CustomException("error");
    }
    //TODO
    public Optional<Offers> findOffersByOrders(Orders orders) {
        return offersRepository.findOffersByOrders(orders);

    }

    public Offers findOffersByOrdersAndProficient(Orders orders, Proficient proficient) {
        Optional<Offers> offers = offersRepository.findOffersByOrdersAndProficient(orders, proficient);
        if(offers.isPresent()){
            return offers.get();
        }else
            throw new NotFoundException("error");
    }

    public Offers findOfferByOrdersAndProficient(long orderId, long proficientId){
        try {
            Optional<Offers> offers = offersRepository.checkExistOffer(proficientId, orderId);
            return offers.orElse(null);
        }catch (Exception e){
            return null;
        }
    }
}
