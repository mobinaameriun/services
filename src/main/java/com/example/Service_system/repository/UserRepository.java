package com.example.Service_system.repository;
import com.example.Service_system.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<Users,Long> {



}
