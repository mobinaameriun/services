package com.example.Service_system.repository;

import com.example.Service_system.entity.Offers;
import com.example.Service_system.entity.Orders;
import com.example.Service_system.entity.Proficient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OffersRepository extends JpaRepository<Offers, Long> {
    Optional<Offers> findOffersByOrders(Orders orders);

    Optional<Offers> findOffersByOrdersAndProficient(Orders orders, Proficient proficient);

    Optional<Offers> findByOrdersAndProficient(Orders orders, Proficient proficient);

    @Query("select o from Offers o where o.orders.id = :orderId and o.proficient.id = :proficientId")
    Optional<Offers> checkExistOffer(long proficientId, long orderId);
}
