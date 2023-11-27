package com.example.Service_system.repository;
import com.example.Service_system.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    Client findUsersByEmailAddressAndPassword(String emailAddress, String password) throws UsernameNotFoundException;
    Client findByIdAndPassword(Long id,String password);
}
