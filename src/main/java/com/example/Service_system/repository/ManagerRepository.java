package com.example.Service_system.repository;
import com.example.Service_system.entity.Client;
import com.example.Service_system.entity.Manager;
import com.example.Service_system.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,Long> {
    @Query("Select u FROM Users u WHERE :param1 is null or u.firstName Like %:param1% and" +
            " :param2 is null or u.lastName Like %:param2% and " +
            " :param3 is null or u.emailAddress Like %:param3% ")
    List<Users> filter(@Param("param1") String param1 ,
                       @Param("param2") String aram2 ,
                       @Param("param3") String aram3);

    Optional<Manager> findManagerByEmailAddressAndPassword(String emailAddress, String password);
}
