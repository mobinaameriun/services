package com.example.Service_system.repository;
import com.example.Service_system.entity.Client;
import com.example.Service_system.entity.Orders;
import com.example.Service_system.entity.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {

    List<Orders> findByClient(Client client);
    List<Orders> findBySubService(SubService subService);
    @Query("select o from Orders o where o.subService.id = :subServiceId and o.orderStatus = 0 or o.orderStatus = 1")
    List<Orders> findOpenOrdersBySubService(long subServiceId);
}
